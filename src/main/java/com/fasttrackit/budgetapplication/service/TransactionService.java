package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.exception.ResourceNotFoundException;
import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.Type;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalLong;

@Service
public class TransactionService {
    private final TransactionReader transactionReader;

    private List<Transaction> transactions;

    long increment = 0;

    public TransactionService(TransactionReader transactionReader) {

        this.transactionReader = transactionReader;
        transactions = transactionReader.getTransactions();

    }

    //GET /transactions - get all transactions.
    // Make it filterable by product , type, minAmount, maxAmount
    public List<Transaction> getAllTransaction() {

        return transactions;
    }

    public List<Transaction> getByProduct(String product) {

        return transactions.stream().filter(transaction -> transaction.getProduct().equals(product)).toList();
    }

    public List<Transaction> getByType(Type type) {
        return transactions.stream().filter(transaction -> transaction.getTypeTrans().equals(type)).toList();
    }

    public Transaction getById(Long id) {
        return transactions.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Product missing", id));
    }

    public List<Transaction> getMinAmount(Double minAmount) {
        return transactions.stream().filter(transaction -> transaction.getAmount() > minAmount).toList();
    }

    public List<Transaction> getMaxAmount(Double maxAmount) {
        return transactions.stream().filter(transaction -> transaction.getAmount() < maxAmount).toList();
    }


    public Transaction add(Transaction transaction) {
        //Long idTrans = Long.valueOf(transactions.size());
        //transaction.setId(idTrans+1);

        increment = Long.valueOf(transactions.size());
        transaction.setId(++increment);
        //transaction.setId(increment+1);

        transactions.add(transaction);
        return transaction;
    }

    public Transaction update(long id, Transaction transaction) {

        Transaction transactionToBeUpdated = getById(id);
        transactionToBeUpdated.setProduct(transaction.getProduct());
        transactionToBeUpdated.setTypeTrans(transaction.getTypeTrans());
        transactionToBeUpdated.setAmount(transaction.getAmount());
        //
        return transactionToBeUpdated;
    }
}
