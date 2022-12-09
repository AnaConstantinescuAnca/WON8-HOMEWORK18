package com.fasttrackit.budgetapplication.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Data    // le cuprinde pe toate cele de sus
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String product;
    @Column
    private TransactionType type;
    @Column
    private double amount;

}