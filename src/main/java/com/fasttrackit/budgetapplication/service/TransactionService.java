package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.exception.ResourceNotFoundException;
import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Transaction> getAll(TransactionType type, Double minAmount, Double maxAmount) {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsFiltered(TransactionType type, Double minAmount, Double maxAmount) {
        if (type != null) {
            if (minAmount != null) {
                if (maxAmount != null) {
                    return getByTypeAndMinAndMax(type.toString(), minAmount, maxAmount);
                }
                return getByTypeAndMin(type.toString(), minAmount);
            } else {
                if (maxAmount != null) {
                    return getByTypeAndMax(type.toString(), maxAmount);
                }
                return getByType(String.valueOf(type));
            }

        } else {
            if (minAmount != null) {
                if (maxAmount != null) {
                    return getByMinAndMax(minAmount, maxAmount);
                }
                return getByMinAmount(minAmount);
            } else {
                if (maxAmount != null) {
                    return getByMaxAmount(maxAmount);
                }
                return getAll(type, minAmount, maxAmount);
            }
        }
    }


    public List<Transaction> getByProduct(String product) {
        return transactionRepository.findByProduct(product);
    }

    //byType
    public List<Transaction> getByType(String type) {
        return transactionRepository.findByType(TransactionType.valueOf(type));
    }

    //by minAmount
    public List<Transaction> getByMinAmount(Double minAmount) {
        return transactionRepository.findByMinAmountQuery(minAmount);
    }

    //by maxAmount
    public List<Transaction> getByMaxAmount(Double maxAmount) {
        return transactionRepository.findByMaxAmountQuery(maxAmount);
    }

    //byMinAndMax
    public List<Transaction> getByMinAndMax(Double minAmount, Double maxAmount) {
        return transactionRepository.findByMinAndMax(minAmount, maxAmount);
    }

    //byTypeAndMin
    public List<Transaction> getByTypeAndMin(String type, Double minAmount) {
        return transactionRepository.findByTypeAndMin(TransactionType.valueOf(type), minAmount);
    }

    //byTypeAndMax
    public List<Transaction> getByTypeAndMax(String type, Double maxAmount) {
        return transactionRepository.findByTypeAndMax(TransactionType.valueOf(type), maxAmount);
    }

    //byTypeAndMinAndMax
    public List<Transaction> getByTypeAndMinAndMax(String type, Double minAmount, Double maxAmount) {
        return transactionRepository.findByTypeAndMinAndMax(TransactionType.valueOf(type), minAmount, maxAmount);
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
        return transactionRepository.save(transactionToBeUpdated);
    }

    public Transaction deleteById(long id) {
        Transaction transactionToBeDeleted = getById(id);
        transactionRepository.deleteById(id);
        return transactionToBeDeleted;
    }


    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
        return transactionReader.getTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<String, List<Transaction>> getTransactionsByProduct() {
        return transactionReader.getTransactions()
                .stream()
                .collect(Collectors.groupingBy(Transaction::getProduct));
    }

}
