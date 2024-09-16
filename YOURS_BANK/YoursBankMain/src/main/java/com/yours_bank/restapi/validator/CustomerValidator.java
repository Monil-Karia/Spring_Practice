package com.yours_bank.restapi.validator;

import com.yours_bank.restapi.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {

    public void validateCustomer(Customer customer) {
        // Validate email format
        if (customer.getEmail() == null || !customer.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidCustomerException("Invalid email format.");
        }

        // Validate phone number
        if (customer.getPhoneNumber() == null || !customer.getPhoneNumber().matches("\\d{10}")) {
            throw new InvalidCustomerException("Phone number must be 10 digits.");
        }

    }
}
