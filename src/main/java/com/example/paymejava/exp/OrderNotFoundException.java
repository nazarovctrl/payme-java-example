package com.example.paymejava.exp;

import org.springframework.web.bind.annotation.ResponseStatus;


public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
