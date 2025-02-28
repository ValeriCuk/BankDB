package org.example.User;

import org.example.Entities.User;

public interface UserDAO {
    void addUser(User user);
    void showUsers();
    User getUser(int id);
}
