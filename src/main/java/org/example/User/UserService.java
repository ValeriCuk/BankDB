package org.example.User;

import lombok.Getter;
import org.example.BankUtil;
import org.example.Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Scanner;

public class UserService {

    @Getter
    private final UserDAO userDAO;
    private final Scanner scanner = new Scanner(System.in);
    private final EntityManager em;

    public UserService() {
        this.em = BankUtil.getEntityManager();
        this.userDAO = new UserDAOImpl(em);
    }

    public void addUser(User user){
        EntityTransaction emTransaction = em.getTransaction();

        try{
            emTransaction.begin();
            userDAO.addUser(user);
            emTransaction.commit();
            System.out.println("User added successfully\nList -> ");
            printList(getAllUsers());
        } catch (Exception e) {
            System.out.println("Error while added user");
            emTransaction.rollback();
        }
    }

    public void printAllUsers(){
        printList(getAllUsers());
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public User getUserById(int id){
        return userDAO.getUserBy(id);
    }

    public void addUsers(){
        addUser(new User("user_first"));
        addUser(new User("user_second"));
    }

    public User selectUser(){
        printList(getAllUsers());

        System.out.println("Enter user id");
        int userId = getIntInput();

        if (userId < 0) return null;
        return getUserById(userId);
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
