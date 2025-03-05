package org.example.User;

import org.example.Entities.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);
    List<User> getAllUsers();
    User getUserBy(int id);
}
