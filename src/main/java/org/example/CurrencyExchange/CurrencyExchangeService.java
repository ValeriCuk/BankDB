package org.example.CurrencyExchange;

import org.example.CurrencyBank;
import org.example.Entities.CurrencyExchange;

public class CurrencyExchangeService {

    private CurrencyExchangeDAO currencyExchangeDAO;

    public CurrencyExchangeService(){
        currencyExchangeDAO = new CurrencyExchangeDAOImpl();
    }

    public void addCourses(){
        currencyExchangeDAO.addCourse(new CurrencyExchange(CurrencyBank.USD, 41.40, 41.90));
        currencyExchangeDAO.addCourse(new CurrencyExchange(CurrencyBank.EUR, 43.42, 43.95));
    }

}
