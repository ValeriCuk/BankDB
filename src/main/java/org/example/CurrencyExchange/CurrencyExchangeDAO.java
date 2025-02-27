package org.example.CurrencyExchange;

import org.example.Entities.CurrencyExchange;

public interface CurrencyExchangeDAO {
    void addCourse(CurrencyExchange currencyExchange);
    void showCourses();
}
