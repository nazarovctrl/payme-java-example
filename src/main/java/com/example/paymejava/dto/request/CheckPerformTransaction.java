package com.example.paymejava.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckPerformTransaction {
    private Long amount;
    private Account account;
}
