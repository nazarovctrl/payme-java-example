package com.example.paymejava.controller;

import com.example.paymejava.dto.result.Error;
import com.example.paymejava.dto.result.Result;
import com.example.paymejava.exp.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({OrderNotFoundException.class})
    private ResponseEntity<?> handler(OrderNotFoundException e) {
        return ResponseEntity.ok(new Result(new Error(-31050, e.getMessage(), "order")));
    }

    @ExceptionHandler({WrongAmountException.class})
    private ResponseEntity<?> handler(WrongAmountException e) {
        return ResponseEntity.ok(new Result(new Error(-31001, e.getMessage(), "amount")));
    }

    @ExceptionHandler({UnableCompleteException.class})
    private ResponseEntity<?> handler(UnableCompleteException e) {
        return ResponseEntity.ok(new Result(new Error(-31008, e.getMessage(), "transaction")));
    }

    @ExceptionHandler({UnableCancelTransaction.class})
    private ResponseEntity<?> handler(UnableCancelTransaction e) {
        return ResponseEntity.ok(new Result(new Error(-31007, e.getMessage(), "transaction")));
    }

    @ExceptionHandler({TransactionNotFoundException.class})
    private ResponseEntity<?> handler(TransactionNotFoundException e) {
        return ResponseEntity.ok(new Result(new Error(-31003, e.getMessage(), "transaction")));
    }

    @ExceptionHandler({TransactionInWaiting.class})
    private ResponseEntity<?> handler(TransactionInWaiting e) {
        return ResponseEntity.ok(new Result(new Error(-31099, e.getMessage(), "transaction")));
    }

    @ExceptionHandler({OrderAlreadyPayed.class})
    private ResponseEntity<?> handler(OrderAlreadyPayed e) {
        return ResponseEntity.ok(new Result(new Error(-31099, e.getMessage(), "order payed/canceled")));
    }
}
