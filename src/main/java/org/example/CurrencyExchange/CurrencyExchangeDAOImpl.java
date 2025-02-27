package org.example.CurrencyExchange;

import org.example.BankUtil;
import org.example.Entities.CurrencyExchange;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CurrencyExchangeDAOImpl implements CurrencyExchangeDAO {

    private final EntityManager em;

    public CurrencyExchangeDAOImpl() {
        this.em = BankUtil.getEntityManager();
    }

    @Override
    public void addCourse(CurrencyExchange currencyExchange) {
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(currencyExchange);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void showCourses() {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CurrencyExchange> cq = cb.createQuery(CurrencyExchange.class);
            Root<CurrencyExchange> root = cq.from(CurrencyExchange.class);

            cq.select(root);
            List<CurrencyExchange> currencyExchange = em.createQuery(cq).getResultList();
            printList(currencyExchange);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void printList(List<CurrencyExchange> list){
        for (CurrencyExchange item : list) {
            System.out.println(item);
        }
    }
}
