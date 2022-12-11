package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findAll();
    List<Transaction> findByProduct(String product);

    @Query("select t from Transaction t where t.product=:name")
    List<Transaction> findByProductByQuery(@Param("name")String product);

    //@Query("select t from Transaction t where t.type =:val")
    List<Transaction> findByType(TransactionType type);

    @Query("select t from Transaction t where t.amount >=:minAmount")
    List<Transaction> findByMinAmountQuery(@Param("minAmount") Double minAmount);
    @Query("select t from Transaction t where t.amount<=:maxAmount")
    List<Transaction> findByMaxAmountQuery(@Param("maxAmount") Double maxAmount);


    @Query("select t from Transaction where t.type=:type and t.amount>=:minAmount")
    List<Transaction> findByTypeAndMin(@Param("type") Transaction type, @Param("minAmount") Double minAmount);

    @Query("select t from Transaction where t.type=:type and t.amount<=:maxAmount")
    List<Transaction> findByTypeAndMax(@Param("type") Transaction type, @Param("maxAmount") Double maxAmount);

    @Query("select t from Transaction where t.amount >=:minAmount and t.amount<=:maxAmount")
    List<Transaction> findByMinAndMax(@Param("minAmount") Double minAmount, @Param("maxAmount") Double maxAmount);

    @Query("select t from Transaction where t.type=:type and and t.amount>=:minAmount and t.amount<=:maxAmount")
    List<Transaction> findByTypeAndMinAndMax(@Param("type") Transaction type,
                                             @Param("minAmount") Double minAmount,
                                             @Param("maxAmount") Double maxAmount);


//    @Query("select t from Transaction where t.type=:type group by t.type" )
//    Map<TransactionType, List<Transaction>> findTransactionByType(@Param("type"));


    //GET /transactions - get all transactions.
    // Make it filterable by type, minAmount, maxAmount
    // (you will have 6 filtering methods in repository: byType, byMinAmoun, byMaxAmout,
    // byTypeAndMin, byTypeAndMax, byMinAndMax, byTypeAndMinAndMax)

}
