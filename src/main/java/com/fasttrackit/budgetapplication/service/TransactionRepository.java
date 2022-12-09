package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //List<Transaction> findAll(Transaction transaction);
    List<Transaction> findByProduct(String product);

    @Query("select t from Transaction t where t.product=:name")
    List<Transaction> findByProductByQuery(@Param("name")String product);

    //@Query("select t from Transaction t where t.type =:value")
    List<Transaction> findByType(TransactionType type);

   @Query("select t from Transaction t where t.amount <=:minAmount")
    List<Transaction> findByMinAmountQuery(@Param("minAmount") Double value);



    //GET /transactions - get all transactions.
    // Make it filterable by type, minAmount, maxAmount
    // (you will have 6 filtering methods in repository: byType, byMinAmoun, byMaxAmout,
    // byTypeAndMin, byTypeAndMax, byMinAndMax, byTypeAndMinAndMax)


}
