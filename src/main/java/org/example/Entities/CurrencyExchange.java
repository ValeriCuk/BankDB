package org.example.Entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class CurrencyExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "buy_rate", nullable = false)
    private BigDecimal buyRate;

    @Column(name = "sell_rate", nullable = false)
    private BigDecimal sellRate;
}
