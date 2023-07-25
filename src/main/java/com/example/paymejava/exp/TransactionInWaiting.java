package com.example.paymejava.exp;

public class TransactionInWaiting extends RuntimeException {
    public TransactionInWaiting(String message) {
        super(message);
    }
}
