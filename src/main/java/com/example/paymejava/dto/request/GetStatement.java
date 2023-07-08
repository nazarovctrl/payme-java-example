package com.example.paymejava.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetStatement {
    private long from;
    private long to;
}
