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
                System.out.println("2: ...");
                System.out.println("3: ...");
                System.out.println("4: ...");
                System.out.println("5: ...");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        bankService.deposit();
                        break;
                    case "2":
//                        ms.showMenu();
                        break;
                    case "3":
//                        ms.showWithPriceRange();
                        break;
                    case "4":
//                        ms.showWithDiscount();
                        break;
                    case "5":
//                        ms.placeOrder();
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