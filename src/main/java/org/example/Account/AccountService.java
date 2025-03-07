package org.example.Account;

import org.example.BankUtil;
import org.example.CurrencyBank;
import org.example.CurrencyExchange.CurrencyExchangeService;
import org.example.DtCt;
import org.example.Entities.Account;
import org.example.Entities.Transaction;
import org.example.Entities.User;
import org.example.Transaction.TransactionDAO;
import org.example.Transaction.TransactionDAOImpl;
import org.example.User.UserDAO;
import org.example.User.UserDAOImpl;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AccountService {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final UserDAO userDAO;
    private final Scanner scanner = new Scanner(System.in);
    private final EntityManager em;
    private final CurrencyExchangeService currencyExchangeService;

    public AccountService() {
        this.em = BankUtil.getEntityManager();
        this.accountDAO = new AccountDAOImpl(em);
        this.transactionDAO = new TransactionDAOImpl(em);
        this.userDAO = new UserDAOImpl(em);
        this.currencyExchangeService = new CurrencyExchangeService();
    }

    public void addAccount(Account account){
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            int userId = account.getUser().getId();
            if (userDAO.getUserBy(userId) == null) {
                userDAO.addUser(account.getUser());
            }

            if (accountDAO.isExist(account)) {
                System.out.println("Account already exists");
                transaction.rollback();
                return;
            }

            accountDAO.addAccount(account);
            transaction.commit();
            System.out.println("Account added");
        }catch (Exception e){
            transaction.rollback();
        }
    }

    public void updateAccount(Account account) {
        accountDAO.updateAccount(account);
    }

    public void printAllAccounts(){
        printList(accountDAO.getAllAccounts());
    }

    public List<Account> getAccountsWith(User user){
        return accountDAO.getAccountsWith(user);
    }

    public Account getAccountBy(int id) {
        if (accountDAO.getAccountBy(id) == null) System.out.println("Account does not exist");
        return accountDAO.getAccountBy(id);
    }

    public double getBalance(Account account) {
        return accountDAO.getBalance(account);
    }

    public void deposit(double amount, Account account) {
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            account.setBalance(account.getBalance() + amount);
            updateAccount(account);

            Transaction transaction1 = new Transaction(account, DtCt.DEBIT, amount, new Date());
            transactionDAO.addTransaction(transaction1);
            System.out.println("Deposit successful");
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            System.out.println("Deposit failed");
        }
    }

    public void transfer(double amount, Account fromAccount, Account toAccount){
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            if (fromAccount.getBalance() < amount) {
                transaction.rollback();
                System.out.println("Insufficient funds in the sender's account");
            }
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            Transaction transaction1 = new Transaction(fromAccount, DtCt.CREDIT, amount, new Date());
            transactionDAO.addTransaction(transaction1);
            updateAccount(fromAccount);

            toAccount.setBalance(toAccount.getBalance() + amount);
            Transaction transaction2 = new Transaction(toAccount, DtCt.DEBIT, amount, new Date());
            transactionDAO.addTransaction(transaction2);
            updateAccount(toAccount);

            transaction.commit();
            System.out.println("Transfer successful");
        }catch (Exception e){
            if(transaction.isActive()) {
                transaction.rollback();
                System.out.println("Transfer failed");
            }
        }
    }

    public void internalTransfer(double amount, Account fromAccount, Account toAccount){
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            if (fromAccount.getBalance() < amount) {
                transaction.rollback();
                System.out.println("Insufficient funds in the sender's account");
            }
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            Transaction transaction1 = new Transaction(fromAccount, DtCt.CREDIT, amount, new Date());
            transactionDAO.addTransaction(transaction1);
            updateAccount(fromAccount);

            double amountFromUAH = amount * currencyExchangeService.getBuyingPrice(fromAccount.getCurrencyBank());
            double amountTo = amountFromUAH / currencyExchangeService.getSellingPrice(toAccount.getCurrencyBank());
            toAccount.setBalance(toAccount.getBalance() + amountTo);
            Transaction transaction2 = new Transaction(toAccount, DtCt.DEBIT, amountTo, new Date());
            transactionDAO.addTransaction(transaction2);
            updateAccount(toAccount);
            transaction.commit();
            System.out.println("Transfer successful");
        }catch (Exception e){
            if(transaction.isActive()) {
                transaction.rollback();
                System.out.println("Transfer failed");
            }
        }
    }

    public void showBalance(){
        printList(accountDAO.getAllAccounts());
        System.out.println("Enter account ID");
        int id = getIntInput();
        if (id < 0) return;
        Account account = accountDAO.getAccountBy(id);
        if (account == null) {
            System.out.println("Account does not exist");
            showBalance();
        }
        System.out.println("Balance: " + getBalance(account));
    }

    public void showGeneralBalance(User user){
        List<Account> userAccounts = accountDAO.getAccountsWith(user);
        double genBalance = 0.0;
        for (Account account : userAccounts) {
            double balanceUAH = getBalance(account) * currencyExchangeService.getBuyingPrice(account.getCurrencyBank());
            genBalance += balanceUAH;
        }
        System.out.println("General balance of " + user.toString() + "\n-> " + genBalance);
    }

    public void addAccounts() {
        User userFirst = userDAO.getUserBy(1) == null ? new User("custom_name1") : userDAO.getUserBy(1);
        Account account1UAH = new Account(123, CurrencyBank.UAH, 0.0, userFirst);
        Account account1USD = new Account(456, CurrencyBank.USD, 0.0, userFirst);
        Account account1EUR = new Account(789, CurrencyBank.EUR, 0.0, userFirst);
        addAccount(account1UAH);
        addAccount(account1USD);
        addAccount(account1EUR);


        User userSecond = userDAO.getUserBy(2) == null ? new User("custom_name2") : userDAO.getUserBy(2);
        Account account2UAH = new Account(123321, CurrencyBank.UAH, 0.0, userSecond);
        Account account2USD = new Account(456654, CurrencyBank.USD, 0.0, userSecond);
        Account account2EUR = new Account(789987, CurrencyBank.EUR, 0.0, userSecond);
        addAccount(account2UAH);
        addAccount(account2USD);
        addAccount(account2EUR);
    }

    public Account selectAccount(User user) {
        List<Account> userAccounts = getAccountsWith(user);

        if (userAccounts.isEmpty()) {
            System.out.println("The user has no accounts.");
            return null;
        } else {
            System.out.println("User accounts:");
            for (Account account : userAccounts) {
                System.out.printf("ID: %d, Number: %s, Account currency: %s, Balance: %.2f%n",
                        account.getId(), account.getNumber(), account.getCurrencyBank(), account.getBalance());
            }
        }

        System.out.println("Enter account id");
        int accountId = getIntInput();
        if (accountId < 0) return null;
        Account account = findAccountById(userAccounts, accountId);
        if (account == null) System.out.println("No such account");
        return account;
    }

    public Account getAccountFromWhich() {
        printAllAccounts();
        System.out.println("Enter the account id from which the transfer will be made:");
        int accountIdFrom = getIntInput();
        return getAccountBy(accountIdFrom);
    }

    public Account getAccountWithSame(CurrencyBank currencyBank) {
        printAccountWith(currencyBank);
        System.out.println("Enter the account id from the list above to which the transfer will be made:");
        int accountIdTo = getIntInput();
        Account to = findAccountById(getAccountsWith(currencyBank), accountIdTo);
        if (to == null) return null;
        return to;
    }

    private List<Account> getAccountsWith(CurrencyBank currencyBank) {
        return accountDAO.getAccountsWith(currencyBank);
    }

    private void printAccountWith(CurrencyBank currencyBank) {
        printList(getAccountsWith(currencyBank));
    }

    private int getIntInput() {
        while (true) {
            System.out.print("-> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return -1;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("-> Invalid input! '" + input + "' is not a valid integer. Try again\n");
            }
        }
    }

    private Account findAccountById(List<Account> accounts, int accountId) {
        Optional<Account> account = accounts.stream()
                .filter(a -> a.getId() == accountId)
                .findFirst();

        return account.orElse(null);
    }

    private <T> void printList(List<T> list){
        for (T item : list) {
            System.out.println(item);
        }
    }
}
