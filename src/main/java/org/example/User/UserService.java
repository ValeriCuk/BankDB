package org.example.User;

import lombok.Getter;
import org.example.Entities.User;

import java.util.List;
import java.util.Scanner;

public class UserService {

    @Getter
    private final UserDAO userDAO;
    private final Scanner scanner = new Scanner(System.in);

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public void addUsers(){
        userDAO.addUser(new User("user_first"));
        userDAO.addUser(new User("user_second"));
    }

    public User selectUser(){
        printList(userDAO.getAllUsers());

        System.out.println("Enter user id");
        int userId = getIntInput();

        if (userId < 0) return null;
        return userDAO.getUserBy(userId);
    }

    private int getIntInput() {
        while (true) {
            System.out.print("-> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return -1;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("-> Invalid input! '" + input + "' is not a valid integer. Try again\n");
            }
        }
    }


    private void printList(List<User> list){
        for (User item : list) {
            System.out.println(item);
        }
    }
}
