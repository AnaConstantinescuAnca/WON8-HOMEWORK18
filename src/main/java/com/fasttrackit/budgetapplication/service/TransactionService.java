package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.exception.ResourceNotFoundException;
import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionReader transactionReader;

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionReader transactionReader, TransactionRepository transactionRepository) {

        this.transactionReader = transactionReader;
        this.transactionRepository = transactionRepository;
        transactionRepository.saveAll(transactionReader.getTransactions());

    }

    //GET /transactions - get all transactions.
    public List<Transaction> getAll() {

        return transactionRepository.findAll();

    }

    public List<Transaction> getByProduct(String product) {

        return transactionRepository.findByProduct(product);
        // return transactionRepository.findByProductByQuery(product);
    }

    //byType
    public List<Transaction> getByType(String type) {

        return transactionRepository.findByType(TransactionType.valueOf(type));
    }

    //by minAmount
    public List<Transaction> getByMinAmount(Double minAmount) {
        return transactionRepository.findByMinAmountQuery(minAmount);

    }

    public Transaction getById(Long id) {
        //return transactionRepository.getReferenceById(id);
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found", id));
    }

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction update(long id, Transaction transaction) {

        Transaction transactionToBeUpdated = getById(id);
        transactionToBeUpdated.setProduct(transaction.getProduct());
        transactionToBeUpdated.setAmount(transaction.getAmount());

        return transactionToBeUpdated;
    }

    public Transaction deleteById(long id) {
        Transaction transactionToBeDeleted = getById(id);
        transactionRepository.deleteById(id);
        return transactionToBeDeleted;
    }

//    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
//        return transactions.stream().collect(Collectors.groupingBy(Transaction::getType));
//    }
//
//    public Map<String, List<Transaction>> getTransactionsByProduct() {
//        return transactions.stream().collect(Collectors.groupingBy(Transaction::getProduct));
//    }
}
