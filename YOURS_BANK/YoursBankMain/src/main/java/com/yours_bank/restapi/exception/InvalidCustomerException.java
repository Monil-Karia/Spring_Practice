package com.yours_bank.restapi.exception;

public class InvalidCustomerException extends RuntimeException {

    public InvalidCustomerException(String message) {
        super(message);
    }
}
