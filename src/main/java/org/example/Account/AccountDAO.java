package org.example.Account;

import org.example.Entities.Account;
import org.example.Entities.User;

import java.util.List;

public interface AccountDAO {
    void addAccount(Account account);
    boolean isExist(Account account);
    void updateAccount(Account account);
    List<Account> getAllAccounts();
    List<Account> getAccountsWith(User user);
    Account getAccountBy(int id);
    double getBalance(Account account);
}
