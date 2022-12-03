package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.exception.ResourceNotFoundException;
import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransactionService {
    private final List<Transaction> transactions;
    private final TransactionReader transactionReader;


    long increment = 0;

    public TransactionService(TransactionReader transactionReader) {

        this.transactionReader = transactionReader;
        transactions = transactionReader.getTransactions();

    }

    //GET /transactions - get all transactions.
    // Make it filterable by product , type, minAmount, maxAmount
    public List<Transaction> getAll(String product, TransactionType type, Double minAmount, Double maxAmount) {
        Stream<Transaction> stream = transactions.stream();
        if (product != null) {
            stream = stream.filter(transaction -> transaction.getProduct().equals(product));
        }
        if (type != null) {
            stream = stream.filter(transaction -> transaction.getType().equals(type));
        }
        if (minAmount != null) {
            stream = stream.filter(transaction -> transaction.getAmount() >= minAmount);
        }
        if (maxAmount != null) {
            stream = stream.filter(transaction -> transaction.getAmount() <= maxAmount);
        }

        return stream.collect(Collectors.toList());

    }


    public Transaction getById(Long id) {
        return transactions.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product missing", id));
    }


    //
    public Transaction add(Transaction transaction) {
        Transaction newTransaction = Transaction.builder()
                .product(transaction.getProduct())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .id(transaction.getId()).build();
        transactions.add(newTransaction);
        return newTransaction;
    }

    public Transaction update(long id, Transaction transaction) {

        Transaction transactionToBeUpdated = getById(id);
        transactionToBeUpdated.setProduct(transaction.getProduct());
        //?! nu pot face SET pe type pt ca e ENUM
        // transactionToBeUpdated.setType(TransactionType.SELL);

        transactionToBeUpdated.setAmount(transaction.getAmount());

        return transactionToBeUpdated;
    }

    public Transaction delete(long id) {
        Transaction transactionToBeDeleted = getById(id);
        transactions.remove(transactionToBeDeleted);
        return transactionToBeDeleted;

    }

    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<String, List<Transaction>> getTransactionsByProduct() {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::getProduct));
    }
}
