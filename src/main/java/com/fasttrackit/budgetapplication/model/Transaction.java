package com.fasttrackit.budgetapplication.model;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Data    // le cuprinde pe toate cele de sus
@Builder
public class Transaction {

    private long id;

    private String product;

    private TransactionType type;

    private double amount;

}