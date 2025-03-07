package org.example.Transaction;

import org.example.BankUtil;
import org.example.Entities.Transaction;

import javax.persistence.EntityManager;
import java.util.List;

public class TransactionService {

    EntityManager entityManager;
    TransactionDAO transactionDAO;

    public TransactionService() {
        this.entityManager = BankUtil.getEntityManager();
        this.transactionDAO = new TransactionDAOImpl(entityManager);
    }

    public Transaction getTransaction(int id) {
        return transactionDAO.getTransactionById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public void printAllTransactions() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
