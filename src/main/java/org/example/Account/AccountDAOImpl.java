package org.example.Account;

import org.example.BankUtil;
import org.example.Entities.Account;
import org.example.Entities.User;
import org.example.User.UserDAO;
import org.example.User.UserDAOImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private final EntityManager em;
    private final UserDAO userDAO = new UserDAOImpl();

    public AccountDAOImpl() {
        em = BankUtil.getEntityManager();
    }


    @Override
    public void addAccount(Account account) {
        int userId = account.getUser().getId();
        if (em.find(User.class, userId) == null) {
            userDAO.addUser(account.getUser());
        }

        if (isExist(account)) {
            System.out.println("Account already exists");
            return;
        }

        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();
            em.persist(account);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw e;
        }
    }

    private boolean isExist(Account account) {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Account> root = cq.from(Account.class);

            cq.select(cb.count(root)).where(cb.equal(root.get("number"), account.getNumber()));
            Long count = em.createQuery(cq).getSingleResult();
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error while verifying account existence",e);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> root = cq.from(Account.class);

            cq.select(root);
            return em.createQuery(cq).getResultList();
        }catch (Exception e){
            System.out.println("Error while fetching users");
            throw e;
        }
    }

    @Override
    public List<Account> getAccountsWith(User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> root = cq.from(Account.class);

            cq.select(root).where(cb.equal(root.get("user"), user));

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            System.err.println("Error while receiving invoices: " + e.getMessage());
            throw e;
        }
    }

}
