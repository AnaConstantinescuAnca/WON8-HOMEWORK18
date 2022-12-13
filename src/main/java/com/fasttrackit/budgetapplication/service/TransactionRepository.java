package com.fasttrackit.budgetapplication.service;

import com.fasttrackit.budgetapplication.model.Transaction;
import com.fasttrackit.budgetapplication.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //List<Transaction> findAll(Transaction transaction);
    List<Transaction> findByProduct(String product);

    @Query("select t from Transaction t where t.product=:name")
    List<Transaction> findByProductByQuery(@Param("name") String product);

    //@Query("select t from Transaction t where t.type =:value")
    List<Transaction> findByType(TransactionType type);

    //byTypeAndMin
    @Query("select t from Transaction t where t.type=:type and t.amount>=:minAmount")
    List<Transaction> findByTypeAndMin(@Param("type") TransactionType type, @Param("minAmount") Double minAmount);

    //byTypeAndMax
    @Query("select t from Transaction t where t.type=:type and t.amount<=:maxAmount")
    List<Transaction> findByTypeAndMax(@Param("type") TransactionType type, @Param("maxAmount") Double maxAmount);

    //byMinAmount
    @Query("select t from Transaction t where t.amount >=:minAmount")
    List<Transaction> findByMinAmountQuery(@Param("minAmount") Double value);

    //byMaxAmount
    @Query("select t from Transaction t where t.amount<=:maxAmount")
    List<Transaction> findByMaxAmountQuery(@Param("maxAmount") Double maxAmount);

    //byMinAndMax
    @Query("select t from Transaction t where t.amount>=:minAmount and t.amount<=:maxAmount")
    List<Transaction> findByMinAndMax(@Param("minAmount") Double minAmount,
                                      @Param("maxAmount") Double maxAmount);
    // byTypeAndMinAndMax
    @Query("select t from Transaction t where t.type=:type and t.amount>=:minAmount and t.amount<=:maxAmount")
    List<Transaction> findByTypeAndMinAndMax(@Param("type") TransactionType type,
                                       @Param("minAmount") Double minAmount,
                                       @Param("maxAmount") Double maxAmount);


    @Query("select t.type, sum(t.amount) from Transaction t group by t.type")
    Map<TransactionType, Double> groupByType();

    @Query("select t.product, sum(t.amount) from Transaction t group by t.product")
    Map<String, List<Transaction>> groupByProduct();

    //GET /transactions - get all transactions.
    // Make it filterable by type, minAmount, maxAmount
    // (you will have 6 filtering methods in repository: byType, byMinAmoun, byMaxAmout,
    // byTypeAndMin, byTypeAndMax, byMinAndMax, byTypeAndMinAndMax)

}
