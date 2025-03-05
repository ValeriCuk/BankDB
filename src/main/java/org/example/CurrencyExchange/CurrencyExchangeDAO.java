package org.example.CurrencyExchange;

import org.example.Entities.CurrencyExchange;

import java.util.List;

public interface CurrencyExchangeDAO {
    void addCourse(CurrencyExchange currencyExchange);
    List<CurrencyExchange> showCourses();
}
