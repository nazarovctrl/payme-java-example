package com.example.paymejava.dto.result;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PerformTransactionResult {
    private String transaction;
    @SerializedName("perform_time")
    private Long performTime;
    private Integer state;
}
