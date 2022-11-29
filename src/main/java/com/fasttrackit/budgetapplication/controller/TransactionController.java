package com.fasttrackit.budgetapplication.controller;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.Type;
import com.fasttrackit.budgetapplication.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("transactions")  //http://host:port/transactions
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAll(@RequestParam(required = false) String product, @RequestParam(required = false) Type type,
                                    @RequestParam(required = false) Double minAmount,
                                    @RequestParam(required = false) Double maxAmount)
    {

        if (product != null) {

            return transactionService.getByProduct(product);

        }else if(type !=null){
            return transactionService.getByType(type);
        } else if (minAmount != null) {

            return  transactionService.getMinAmount(minAmount);

        } else if (maxAmount!=null) {
            return transactionService.getMaxAmount(maxAmount);
        }
        return transactionService.getAllTransaction();
    }

    @GetMapping("{id}")  //GET  http://host:port/transactions/3
    public Transaction getById(@PathVariable long id) {
        return transactionService.getById(id);
    }

    @PostMapping
    public Transaction add(@RequestBody Transaction transaction){
        return transactionService.add(transaction);
    }

    @PutMapping("{id}")
    public Transaction update(@PathVariable long id, @RequestBody Transaction transaction){
        return transactionService.update(id,transaction);
    }

}
