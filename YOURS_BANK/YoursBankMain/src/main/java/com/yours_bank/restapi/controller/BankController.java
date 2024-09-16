package com.yours_bank.restapi.controller;

import com.yours_bank.restapi.entity.Customer;
import com.yours_bank.restapi.entity.Transaction;
import com.yours_bank.restapi.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BankController {

	@Autowired
	private BankService bankService;

	@PostMapping("/employees/registerCustomer")
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
		Customer registeredCustomer = bankService.registerCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
	}

	@PutMapping("/customers/update/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
		Optional<Customer> customer = bankService.updateCustomer(id, updatedCustomer);
		return customer.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@PostMapping("/employees/credit/{accountId}")
	public ResponseEntity<Transaction> creditAccount(@PathVariable int accountId, @RequestParam double amount) {
		Optional<Transaction> transaction = bankService.creditAccount(accountId, amount);
		return transaction.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@PostMapping("/employees/debit/{accountId}")
	public ResponseEntity<Transaction> debitAccount(@PathVariable int accountId, @RequestParam double amount) {
		Optional<Transaction> transaction = bankService.debitAccount(accountId, amount);
		return transaction.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
	}

	@GetMapping("/customers/{customerId}/transactions")
	public ResponseEntity<List<Transaction>> getCustomerTransactions(@PathVariable int customerId) {
		Optional<List<Transaction>> transactions = bankService.getCustomerTransactions(customerId);
		return transactions.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@GetMapping("/employees/transactions")
	public ResponseEntity<List<Transaction>> getAllTransactions() {
		List<Transaction> transactions = bankService.getAllTransactions();
		return ResponseEntity.ok(transactions);
	}
}
