package org.example.Account;

import org.example.CurrencyBank;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.User.UserDAO;
import org.example.User.UserDAOImpl;

public class AccountService {

    private final AccountDAO accountDAO;
    private final UserDAO userDAO;

    public AccountService() {
        this.accountDAO = new AccountDAOImpl();
        this.userDAO = new UserDAOImpl();
    }

    public void addAccounts() {
        User userFirst = userDAO.getUser(1) == null ? new User("custom_name1") : userDAO.getUser(1);
        Account account1UAH = new Account(123, CurrencyBank.UAH, 0.0, userFirst);
        Account account1USD = new Account(456, CurrencyBank.USD, 0.0, userFirst);
        Account account1EUR = new Account(789, CurrencyBank.EUR, 0.0, userFirst);
        accountDAO.addAccount(account1UAH);
        accountDAO.addAccount(account1USD);
        accountDAO.addAccount(account1EUR);


        User userSecond = userDAO.getUser(2) == null ? new User("custom_name2") : userDAO.getUser(2);
        Account account2UAH = new Account(123321, CurrencyBank.UAH, 0.0, userSecond);
        Account account2USD = new Account(456654, CurrencyBank.USD, 0.0, userSecond);
        Account account2EUR = new Account(789987, CurrencyBank.EUR, 0.0, userSecond);
        accountDAO.addAccount(account2UAH);
        accountDAO.addAccount(account2USD);
        accountDAO.addAccount(account2EUR);
    }

    public void showAccounts(){
        accountDAO.showAccounts();
    }
}
