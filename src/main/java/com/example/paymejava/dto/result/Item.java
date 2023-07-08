package com.example.paymejava.dto.result;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Item {
    private String code;
    private String title;
    private long price;
    private int count;
    @SerializedName("package_code")
    private String packageCode;
    @SerializedName("vat_percent")
    private int vatPercent;
}
