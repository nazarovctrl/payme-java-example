package com.example.paymejava.util;

import com.example.paymejava.dto.request.Account;
import com.example.paymejava.dto.result.CheckTransactionResult;
import com.example.paymejava.dto.result.CreateTransactionResult;
import com.example.paymejava.dto.result.GetStatementResult;
import com.example.paymejava.entity.OrderTransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class MerchantUtil {
    public CreateTransactionResult getCreateTransactionResult(OrderTransactionEntity transaction) {
        return CreateTransactionResult.builder()
                .createTime(transaction.getCreateTime())
                .transaction(transaction.getId().toString())
                .state(transaction.getState().getCode()).build();
    }

    public GetStatementResult getStatementResult(OrderTransactionEntity transaction) {
        GetStatementResult getStatementResult = GetStatementResult.builder()
                .id(transaction.getPaycomId())
                .time(transaction.getPaycomTime())
                .amount(transaction.getOrder().getAmount())
                .account(new Account(transaction.getOrder().getId()))
                .createTime(transaction.getCreateTime())
                .performTime(transaction.getPerformTime())
                .cancelTime(transaction.getCancelTime())
                .transaction(transaction.getId().toString()).build();
        if (transaction.getState() != null) {
            getStatementResult.setState(transaction.getState().getCode());
        }
        if (transaction.getReason() != null) {
            getStatementResult.setReason(transaction.getReason().getCode());
        }
        return getStatementResult;
    }

    public CheckTransactionResult getCheckTransactionresult(OrderTransactionEntity transaction) {
        return CheckTransactionResult.builder()
                .create_time(transaction.getCreateTime())
                .perform_time(transaction.getPerformTime())
                .cancel_time(transaction.getCancelTime())
                .transaction(transaction.getId().toString()).build();
    }
}
