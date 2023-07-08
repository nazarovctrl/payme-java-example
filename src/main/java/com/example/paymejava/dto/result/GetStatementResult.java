package com.example.paymejava.dto.result;

import com.example.paymejava.dto.request.Account;
import com.google.gson.annotations.SerializedName;
import lombok.*;


@Builder
@AllArgsConstructor
@Getter
public class GetStatementResult {
    private String id;
    private long time;
    private Long amount;
    private Account account;
    @SerializedName("create_time")
    private long createTime;
    @SerializedName("perform_time")
    private long performTime;
    @SerializedName("cancel_time")
    private long cancelTime;
    private String transaction;
    private Integer state;
    private Integer reason;

    public void setState(Integer state) {
        this.state = state;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }
}
