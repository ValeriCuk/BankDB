package org.example.Transaction;

import org.example.BankUtil;
import org.example.Entities.Account;
import org.example.Entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private final EntityManager em;

    public TransactionDAOImpl() {
        this.em = BankUtil.getEntityManager();
    }


    @Override
    public void addTransaction(Transaction transaction) {
        EntityTransaction emTransaction = em.getTransaction();
        try {
            emTransaction.begin();
            em.persist(transaction);
            emTransaction.commit();
        }catch (Exception e){
            emTransaction.rollback();
            throw e;
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
            Root<Transaction> root = cq.from(Transaction.class);

            cq.select(root);
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching transactions",e);
        }
    }

    @Override
    public Transaction getTransactionById(int id) {
        return em.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(Account account) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
            Root<Transaction> root = cq.from(Transaction.class);

            cq.select(root).where(cb.equal(root.get("account"), account));

            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.err.println("Error while receiving invoices: " + e.getMessage());
            throw e;
        }
    }
}
