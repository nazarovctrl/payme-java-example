package com.example.paymejava.enums;

import lombok.Getter;

@Getter
public enum OrderCancelReason {
    RECEIVER_NOT_FOUND(1),
    DEBIT_OPERATION_ERROR(2),
    TRANSACTION_ERROR(3),
    TRANSACTION_TIMEOUT(4),
    MONEY_BACK(5),
    UNKNOWN_ERROR(10);
    private final int code;
    OrderCancelReason(int code) {
        this.code = code;
    }
}
