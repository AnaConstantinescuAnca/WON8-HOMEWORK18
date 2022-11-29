package com.fasttrackit.budgetapplication.model;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    private long id;

    private String product;

    private Type typeTrans;

    private double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return id == transaction.id &&
                Double.compare(transaction.amount, amount) == 0 &&
                Objects.equals(product, transaction.product) &&
                Objects.equals(typeTrans, transaction.typeTrans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, typeTrans, amount);
    }
}
