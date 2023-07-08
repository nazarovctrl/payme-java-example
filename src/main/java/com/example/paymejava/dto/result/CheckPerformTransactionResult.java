package com.example.paymejava.dto.result;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckPerformTransactionResult {
    public boolean allow;
    private DetailResult detail;
}
