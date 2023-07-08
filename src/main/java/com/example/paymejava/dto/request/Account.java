package com.example.paymejava.dto.request;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@AllArgsConstructor
@Getter
public class Account {
    @SerializedName("order_id")
    private Long orderId;
}
