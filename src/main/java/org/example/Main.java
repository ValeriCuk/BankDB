package org.example;

import org.example.Account.AccountService;
import org.example.CurrencyExchange.CurrencyExchangeService;
import org.example.User.UserService;

public class Main {

    private static final UserService userService = new UserService();
    private static final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();
    private static final AccountService accountService = new AccountService();

    public static void main(String[] args) {
        initDB();

    }

    private static void initDB(){
        userService.addUsers();
        currencyExchangeService.addCourses();
        accountService.addAccounts();
    }

}