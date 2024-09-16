package com.yours_bank.restapi.repository;


import com.yours_bank.restapi.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    // Find a customer by email for login purposes
    Customer findByEmail(String email);
}
