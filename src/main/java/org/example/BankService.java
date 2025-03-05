package org.example;

import lombok.Getter;
import org.example.Account.AccountService;
import org.example.CurrencyExchange.CurrencyExchangeService;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.User.UserService;

import java.util.Scanner;

public class BankService {

    @Getter
    private final AccountService accountService = new AccountService();
    @Getter
    private final UserService userService = new UserService();
    @Getter
    private final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();
    private final Scanner scanner = new Scanner(System.in);

    public BankService() {}

    public void deposit(){
        User user = userService.selectUser();
        if (user == null) {
            System.out.println("The user with the entered id does not exist.");
            return;
        }
        Account selectedAccount = accountService.selectAccount(user);
        if (selectedAccount == null) deposit();
        System.out.println(selectedAccount);
        double amount = getAmount();
        if (amount < 0) deposit();

    }

    private double getAmount() {
        double amount;
        while (true) {
            System.out.println("Enter the top-up amount");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("-> back\n");
                return -1.0;
            }
            String strPrice = input.trim().replace(',', '.');
            try {
                amount = Double.parseDouble(strPrice);
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("-> Invalid input! " + strPrice + "\n");
            }
        }
    }
/*
void deposit(double amount, Account account);
    void transfer(double amount, Account fromAccount, Account toAccount);
    void internalTransfer(double amount, Account fromAccount, Account toAccount);
    void showBalance(Account account);
    void showGeneralBalance(User user);
 */
}
