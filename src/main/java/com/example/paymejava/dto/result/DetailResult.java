package com.example.paymejava.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DetailResult {
    private int receipt_type;
    private List<Item> items;
}
