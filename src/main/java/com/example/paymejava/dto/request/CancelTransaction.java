package com.example.paymejava.dto.request;

import com.example.paymejava.enums.OrderCancelReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelTransaction {
    private String id;
    private OrderCancelReason reason;
}
