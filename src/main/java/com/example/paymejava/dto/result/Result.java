package com.example.paymejava.dto.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Result {
    private Object result;
    private Error error;

    public Result(Error error) {
        this.error = error;
    }
}
