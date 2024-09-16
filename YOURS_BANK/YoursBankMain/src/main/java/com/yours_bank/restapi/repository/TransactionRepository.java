package com.yours_bank.restapi.repository;

import com.yours_bank.restapi.entity.Transaction;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    // Find transactions by account ID
    List<Transaction> findByAccount_AccountId(int accountId);
}
