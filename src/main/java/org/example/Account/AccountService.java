package org.example.Account;

import org.example.CurrencyBank;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.User.UserDAO;
import org.example.User.UserDAOImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AccountService {

    private final AccountDAO accountDAO;
    private final UserDAO userDAO;
    private final Scanner scanner = new Scanner(System.in);

    public AccountService() {
        this.accountDAO = new AccountDAOImpl();
        this.userDAO = new UserDAOImpl();
    }

    public void addAccounts() {
        User userFirst = userDAO.getUserBy(1) == null ? new User("custom_name1") : userDAO.getUserBy(1);
        Account account1UAH = new Account(123, CurrencyBank.UAH, 0.0, userFirst);
        Account account1USD = new Account(456, CurrencyBank.USD, 0.0, userFirst);
        Account account1EUR = new Account(789, CurrencyBank.EUR, 0.0, userFirst);
        accountDAO.addAccount(account1UAH);
        accountDAO.addAccount(account1USD);
        accountDAO.addAccount(account1EUR);


        User userSecond = userDAO.getUserBy(2) == null ? new User("custom_name2") : userDAO.getUserBy(2);
        Account account2UAH = new Account(123321, CurrencyBank.UAH, 0.0, userSecond);
        Account account2USD = new Account(456654, CurrencyBank.USD, 0.0, userSecond);
        Account account2EUR = new Account(789987, CurrencyBank.EUR, 0.0, userSecond);
        accountDAO.addAccount(account2UAH);
        accountDAO.addAccount(account2USD);
        accountDAO.addAccount(account2EUR);
    }


    public void showAccounts(){
        printList(accountDAO.getAllAccounts());
    }

    public Account selectAccount(User user) {
        List<Account> userAccounts = accountDAO.getAccountsWith(user);

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

    private void printList(List<Account> list){
        for (Account item : list) {
            System.out.println(item);
        }
    }

    public void deposit(double amount, Account account) {

    }
}
