package com.yours_bank.restapi.repository;

import com.yours_bank.restapi.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    // Find account by account number for operations
    Account findByAccountNumber(String accountNumber);
}
