package org.example.Entities;

import lombok.Data;
import org.example.DtCt;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private DtCt dtCt;

    @Column(nullable = false)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    public Transaction() {
    }

    public Transaction(Account account, DtCt dtCt, BigDecimal amount, Date date) {
        this.account = account;
        this.dtCt = dtCt;
        this.amount = amount;
        this.date = date;
    }
}
