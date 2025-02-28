package org.example.User;

import org.example.Entities.User;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public void addUsers(){
        userDAO.addUser(new User("user_first"));
        userDAO.addUser(new User("user_second"));
    }

    public void showUsers(){
        userDAO.showUsers();
    }
}
