package org.example.Entities;

import lombok.Data;
import org.example.CurrencyBank;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num", nullable = false, unique = true)
    private int number;

    @Column(name = "curr", nullable = false)
    private CurrencyBank currencyBank;

    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Account() {}

    public Account(int number, CurrencyBank currencyBank, double balance, User user) {
        this.number = number;
        this.currencyBank = currencyBank;
        this.balance = balance;
        this.user = user;
    }

}
