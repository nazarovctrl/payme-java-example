package com.example.paymejava.enums;

import lombok.Getter;

@Getter
public enum TransactionState {
    STATE_NEW(0),
    STATE_IN_PROGRESS(1),
    STATE_DONE(2),
    STATE_CANCELED(-1),
    STATE_POST_CANCELED(-2);
    private final int code;
    TransactionState(int code) {
        this.code = code;
    }
}
