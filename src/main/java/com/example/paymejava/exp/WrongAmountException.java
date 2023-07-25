package com.example.paymejava.exp;


public class WrongAmountException extends RuntimeException {
    public WrongAmountException(String message) {
        super(message);
    }
}
