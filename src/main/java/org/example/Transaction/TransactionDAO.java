package org.example.Transaction;

import org.example.Entities.Account;
import org.example.Entities.Transaction;

import java.util.List;

public interface TransactionDAO {
    void addTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(int id);
    List<Transaction> getTransactionsByAccount(Account account);
}
