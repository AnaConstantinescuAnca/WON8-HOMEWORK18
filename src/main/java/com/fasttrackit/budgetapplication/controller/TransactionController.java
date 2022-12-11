package com.fasttrackit.budgetapplication.controller;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import com.fasttrackit.budgetapplication.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("transactions")  //http://host:port/transactions
public class TransactionController {
    private final TransactionService transactionService;


    @GetMapping()    // http://localhost:8080/transactions
    public List<Transaction> getAll(@RequestParam(required = false) TransactionType type,
                                    @RequestParam(required = false) Double minAmount,
                                    @RequestParam(required = false) Double maxAmount) {
        return transactionService.getAll(type, minAmount, maxAmount);
    }



    @GetMapping("type") // http://host:port/transactions/type?type=SELL
    public List<Transaction> getType(@RequestParam(required = false) String type) {

        return transactionService.getByType(type);
    }

//    @GetMapping("minAmount/{value}") // http://localhost:8080/transaction/minAmount/100
//    public List<Transaction> getAll(@PathVariable Double value){
//
//        return transactionService.getByMinAmount(value);
//
//    }


    //GET /transactions/{id} - get transaction with id
    @GetMapping("{id}")  //GET  http://host:port/transactions/3
    public Transaction getById(@PathVariable long id) {
        return transactionService.getById(id);
    }

    //POST /transactions - adds a new transaction
    @PostMapping
    public Transaction add(@RequestBody Transaction transaction) {
        return transactionService.add(transaction);
    }

    //PUT  /transactions/{id} - replaces the transaction with id
    @PutMapping("{id}")
    public Transaction update(@PathVariable long id, @RequestBody Transaction transaction) {
        return transactionService.update(id, transaction);
    }

    //DELETE /transactions/{id} - deletes the transaction with id
    @DeleteMapping("{id}")
    public Transaction delete(@PathVariable long id) {
        return transactionService.deleteById(id);
    }

    //    GET /transactions/reports/type -> returns a map from type to list of transactions of that type
//    @GetMapping("reports/type")
//    public Map<TransactionType, List<Transaction>> reportByType() {
//        return transactionService.getTransactionsByType();
//    }
//
//
//    //    GET /transactions/reports/product -> returns a map from product to list of transactions for that product
//    @GetMapping("reports/product")
//    public Map<String, List<Transaction>> reportByProduct() {
//        return transactionService.getTransactionsByProduct();
//    }
}
