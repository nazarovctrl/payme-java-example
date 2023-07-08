package com.example.paymejava.service;


import com.example.paymejava.dto.request.*;
import com.example.paymejava.dto.result.*;
import com.example.paymejava.dto.result.PerformTransaction;
import com.example.paymejava.dto.result.PerformTransactionResult;
;

public interface IMerchantService {

    CheckPerformTransactionResult checkPerformTransaction(CheckPerformTransaction checkPerformTransaction);

    CreateTransactionResult createTransaction(CreateTransaction createTransaction);

    PerformTransactionResult performTransaction(PerformTransaction performTransaction);

    CancelTransactionResult cancelTransaction(CancelTransaction fromJson);

    CheckTransactionResult checkTransaction(CheckTransaction checkTransaction);

    Transactions getStatement(GetStatement getStatement);
}
