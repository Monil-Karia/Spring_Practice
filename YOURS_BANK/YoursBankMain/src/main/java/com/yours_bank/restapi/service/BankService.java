package com.yours_bank.restapi.service;

import com.yours_bank.restapi.entity.Account;
import com.yours_bank.restapi.entity.Customer;
import com.yours_bank.restapi.entity.Transaction;
import com.yours_bank.restapi.repository.AccountRepository;
import com.yours_bank.restapi.repository.CustomerRepository;
import com.yours_bank.restapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    public Customer registerCustomer(Customer customer) {
        customer.setCustomerId(generateCustomerId());
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(0.0);
        account.setCustomer(customer);
        customer.setAccount(account);
        customerRepo.save(customer);
        accountRepo.save(account);
        return customer;
    }

    public Optional<Customer> updateCustomer(int id, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepo.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setAddress(updatedCustomer.getAddress());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setPassword(updatedCustomer.getPassword());
            customerRepo.save(customer);
            return Optional.of(customer);
        }
        return Optional.empty();
    }

    public Optional<Transaction> creditAccount(int accountId, double amount) {
        Optional<Account> accountOpt = accountRepo.findById(accountId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setBalance(account.getBalance() + amount);
            accountRepo.save(account);

            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transaction.setType("CREDIT");
            transactionRepo.save(transaction);

            return Optional.of(transaction);
        }
        return Optional.empty();
    }

    public Optional<Transaction> debitAccount(int accountId, double amount) {
        Optional<Account> accountOpt = accountRepo.findById(accountId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                accountRepo.save(account);

                Transaction transaction = new Transaction();
                transaction.setAccount(account);
                transaction.setAmount(amount);
                transaction.setType("DEBIT");
                transactionRepo.save(transaction);

                return Optional.of(transaction);
            }
        }
        return Optional.empty();
    }

    public Optional<List<Transaction>> getCustomerTransactions(int customerId) {
        return customerRepo.findById(customerId)
                .map(customer -> transactionRepo.findByAccount_CustomerId(customerId));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    // Utility methods to generate unique customer ID and account number
    private int generateCustomerId() {
        // Implement ID generation logic here
        return 0; // Placeholder
    }

    private int generateAccountNumber() {
        // Implement account number generation logic here
        return 0; // Placeholder
    }
}
