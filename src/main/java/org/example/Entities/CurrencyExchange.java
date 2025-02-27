package org.example.Entities;

import lombok.Data;
import org.example.CurrencyBank;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class CurrencyExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currency", nullable = false)
    private CurrencyBank currency;

    @Column(name = "buy_rate", nullable = false)
    private double buyRate;

    @Column(name = "sell_rate", nullable = false)
    private double sellRate;

    public CurrencyExchange() {}

    public CurrencyExchange(CurrencyBank currency, double buyRate, double sellRate) {
        this.currency = currency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }
}
