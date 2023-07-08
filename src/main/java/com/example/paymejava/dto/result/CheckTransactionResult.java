package com.example.paymejava.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CheckTransactionResult {
    private long create_time;
    private long perform_time;
    private long cancel_time;
    private String transaction;
    private Integer state;
    private Integer reason;
}
