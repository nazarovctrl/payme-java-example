package com.example.paymejava.dto.result;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTransactionResult {
    @SerializedName("create_time")
    private long createTime;
    private String transaction;
    private Integer state;
}
