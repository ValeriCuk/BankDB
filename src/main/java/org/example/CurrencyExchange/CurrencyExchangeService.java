package org.example.CurrencyExchange;

import org.example.BankUtil;
import org.example.CurrencyBank;
import org.example.Entities.CurrencyExchange;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CurrencyExchangeService {

    private final CurrencyExchangeDAO currencyExchangeDAO;
    private final EntityManager entityManager;

    public CurrencyExchangeService(){
        this.entityManager = BankUtil.getEntityManager();
        this.currencyExchangeDAO = new CurrencyExchangeDAOImpl(entityManager);
    }

    public void addCourse(CurrencyExchange currencyExchange){
        EntityTransaction tx = entityManager.getTransaction();
        try{
            tx.begin();
            currencyExchangeDAO.addCourse(currencyExchange);
            tx.commit();
            System.out.println("Course added");
        }catch (Exception e){
            tx.rollback();
            System.out.println("Course not added");
            throw e;
        }
    }

    public List<CurrencyExchange> getAllCurrencies(){
        return currencyExchangeDAO.getAllCourses();
    }

    public double getSellingPrice(CurrencyBank currencyBank){
        CurrencyExchange currencyExchange = currencyExchangeDAO.getCurrencyExchangeWith(currencyBank);
        if(currencyExchange == null) return -1;
        return currencyExchange.getSellRate();
    }

    public double getBuyingPrice(CurrencyBank currencyBank){
        CurrencyExchange currencyExchange = currencyExchangeDAO.getCurrencyExchangeWith(currencyBank);
        if(currencyExchange == null) return -1;
        return currencyExchange.getBuyRate();
    }

    public void addCourses(){
        addCourse(new CurrencyExchange(CurrencyBank.USD, 41.40, 41.90));
        addCourse(new CurrencyExchange(CurrencyBank.EUR, 43.42, 43.95));
        addCourse(new CurrencyExchange(CurrencyBank.UAH, 1.00, 1.00));
    }

    public void printAllCourses(){
        printList(currencyExchangeDAO.getAllCourses());
    }

    private void printList(List<CurrencyExchange> list){
        for (CurrencyExchange item : list) {
            System.out.println(item);
        }
    }
}
