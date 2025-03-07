package org.example;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final BankService bankService = new BankService();

    public static void main(String[] args) {
        initDB();

        try {
            while (true) {
                System.out.println("1: user account funding");
                System.out.println("2: transfer");
                System.out.println("3: internalTransfer");
                System.out.println("4: showBalance");
                System.out.println("5: showGeneralBalance");
                System.out.println("6: all users");
                System.out.println("7: all accounts");
                System.out.println("8: all transactions");
                System.out.println("9: all currency exchange");

                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        bankService.deposit();
                        break;
                    case "2":
                        bankService.transfer();
                        break;
                    case "3":
                        bankService.internalTransfer();
                        break;
                    case "4":
                        bankService.showAccountBalance();
                        break;
                    case "5":
                        bankService.showUserGeneralBalance();
                        break;
                    case "6":
                        bankService.getUserService().printAllUsers();
                        break;
                    case "7":
                        bankService.getAccountService().printAllAccounts();
                        break;
                    case "8":
                        bankService.getTransactionService().printAllTransactions();
                        break;
                    case "9":
                        bankService.getCurrencyExchangeService().printAllCourses();
                        break;
                    default:
                        return;
                }
            }
        }finally {
            BankUtil.getEntityManager().close();
            BankUtil.shutdown();
        }

    }

    private static void initDB(){
        bankService.getUserService().addUsers();
        bankService.getAccountService().addAccounts();
        bankService.getCurrencyExchangeService().addCourses();
    }

}