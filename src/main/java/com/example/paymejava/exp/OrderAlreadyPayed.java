package com.example.paymejava.exp;

public class OrderAlreadyPayed extends RuntimeException {
    public OrderAlreadyPayed(String message) {
        super(message);
    }
}