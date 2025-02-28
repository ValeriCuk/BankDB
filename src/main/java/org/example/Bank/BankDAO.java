package org.example.Bank;

import org.example.Entities.Account;
import org.example.Entities.User;

public interface BankDAO {
    void deposit(double amount, Account account);
    void transfer(double amount, Account fromAccount, Account toAccount);
    void internalTransfer(double amount, Account fromAccount, Account toAccount);
    void showBalance(Account account);
    void showGeneralBalance(User user);

}
