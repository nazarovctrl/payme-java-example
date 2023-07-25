package com.example.paymejava.exp;

public class UnableCancelTransaction extends RuntimeException {
    public UnableCancelTransaction(String message) {
        super(message);
    }
}
