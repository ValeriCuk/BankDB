package org.example;

import org.example.CurrencyExchange.CurrencyExchangeService;
import org.example.User.UserService;

public class Main {

    private static final UserService userService = new UserService();
    private static final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    public static void main(String[] args) {
        userService.addUsers();
        currencyExchangeService.addCourses();

    }
}