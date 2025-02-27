package org.example.Account;

import org.example.Entities.Account;

public interface AccountDAO {
    void addAccount(Account account);
    void showAccounts();
}
