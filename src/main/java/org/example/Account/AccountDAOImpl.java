package org.example.Account;

import org.example.CurrencyBank;
import org.example.Entities.Account;
import org.example.Entities.User;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private final EntityManager em;

    public AccountDAOImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public void addAccount(Account account) {
        em.persist(account);
    }

    @Override
    public void updateAccount(Account account) {
        Account oldAccount = getAccountBy(account.getId());
        oldAccount.setBalance(account.getBalance());
        em.merge(oldAccount);
    }

    @Override
    public boolean isExist(Account account) {
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

    @Override
    public List<Account> getAccountsWith(CurrencyBank currencyBank){
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Account> cq = cb.createQuery(Account.class);
            Root<Account> root = cq.from(Account.class);

            cq.select(root).where(cb.equal(root.get("currencyBank"), currencyBank));

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            System.err.println("Error while receiving invoices: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Account getAccountBy(int id){
        return em.find(Account.class, id);
    }

    @Override
    public double getBalance(Account account) {
        return getAccountBy(account.getId()).getBalance();
    }
}
