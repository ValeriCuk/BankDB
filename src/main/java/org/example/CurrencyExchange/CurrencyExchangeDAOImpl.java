package org.example.CurrencyExchange;

import org.example.BankUtil;
import org.example.CurrencyBank;
import org.example.Entities.CurrencyExchange;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CurrencyExchangeDAOImpl implements CurrencyExchangeDAO {

    private final EntityManager em;

    public CurrencyExchangeDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addCourse(CurrencyExchange currencyExchange) {
        em.persist(currencyExchange);
    }

    @Override
    public List<CurrencyExchange> getAllCourses() {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CurrencyExchange> cq = cb.createQuery(CurrencyExchange.class);
            Root<CurrencyExchange> root = cq.from(CurrencyExchange.class);

            cq.select(root);
            return em.createQuery(cq).getResultList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public CurrencyExchange getCurrencyExchangeWith(CurrencyBank currencyBank) {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CurrencyExchange> cq = cb.createQuery(CurrencyExchange.class);
            Root<CurrencyExchange> root = cq.from(CurrencyExchange.class);

            cq.select(root).where(cb.equal(root.get("currency"), currencyBank));
            return em.createQuery(cq).getSingleResult();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
