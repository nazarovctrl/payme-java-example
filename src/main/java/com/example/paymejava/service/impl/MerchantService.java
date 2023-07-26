package com.example.paymejava.service.impl;

import com.example.paymejava.dto.request.*;
import com.example.paymejava.dto.result.*;
import com.example.paymejava.entity.OrderEntity;
import com.example.paymejava.entity.OrderTransactionEntity;
import com.example.paymejava.enums.TransactionState;
import com.example.paymejava.exp.*;
import com.example.paymejava.repository.OrderRepository;
import com.example.paymejava.repository.TransactionRepository;
import com.example.paymejava.service.IMerchantService;
import com.example.paymejava.util.MerchantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantService implements IMerchantService {
    private static final Long time_expired = 43_200_000L;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final MerchantUtil merchantUtil;

    private OrderEntity getOrder(Long orderId) {
        Optional<OrderEntity> optional = orderRepository.findById(orderId);

        if (optional.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }
        return optional.get();
    }

    @Override
    public CheckPerformTransactionResult checkPerformTransaction(CheckPerformTransaction checkPerformTransaction) {
        OrderEntity order = getOrder(checkPerformTransaction.getAccount().getOrderId());

        if (!checkPerformTransaction.getAmount().equals(order.getAmount())) {
            throw new WrongAmountException("Wrong amount");
        }
        return CheckPerformTransactionResult.builder()
                .allow(true)
                .detail(new DetailResult(0, List.of(
                        Item.builder()
                                .code("10107002001000000")
                                .title("Услуги по перевозке грузов автомобильным транспортом")
                                .price(order.getAmount())
                                .packageCode("1209885")
                                .vatPercent(0)
                                .build())))
                .build();
    }

    @Override
    public CreateTransactionResult createTransaction(CreateTransaction createTransaction) {
        Optional<OrderTransactionEntity> optional = transactionRepository.findByPaycomId(createTransaction.getId());

        if (optional.isPresent()) {
            OrderTransactionEntity transaction = optional.get();
            if (transaction.getState().equals(TransactionState.STATE_IN_PROGRESS) && System.currentTimeMillis() - transaction.getPaycomTime() < time_expired) {
                return merchantUtil.getCreateTransactionResult(transaction);
            }
            throw new UnableCompleteException("Unable to complete operation");
        }

        if (!checkPerformTransaction(new CheckPerformTransaction(createTransaction.getAmount(),
                createTransaction.getAccount())).isAllow()) {
            throw new UnableCompleteException("Unable to complete operation");
        }

        Optional<OrderTransactionEntity> optionalTransaction = transactionRepository
                .findByOrder_Id(createTransaction.getAccount().getOrderId());

        if (optionalTransaction.isPresent()) {
            throw new TransactionInWaiting("transaction");
        }

        OrderEntity order = getOrder(createTransaction.getAccount().getOrderId());

        OrderTransactionEntity newTransaction = OrderTransactionEntity.builder()
                .paycomId(createTransaction.getId())
                .paycomTime(createTransaction.getTime())
                .order(order).build();

        transactionRepository.save(newTransaction);
        return merchantUtil.getCreateTransactionResult(newTransaction);
    }

    @Override
    public PerformTransactionResult performTransaction(PerformTransaction performTransaction) {
        Optional<OrderTransactionEntity> optional = transactionRepository.findByPaycomId(performTransaction.getId());

        if (optional.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }

        OrderTransactionEntity transaction = optional.get();
        if (transaction.getState().equals(TransactionState.STATE_IN_PROGRESS)) {
            if (System.currentTimeMillis() - transaction.getPaycomTime() < time_expired) {
                transaction.setState(TransactionState.STATE_DONE);
                transaction.setPerformTime(new Date().getTime());
                transactionRepository.save(transaction);
                return new PerformTransactionResult(transaction.getId().toString(), transaction.getPerformTime(), transaction.getState().getCode());
            }
            transaction.setState(TransactionState.STATE_CANCELED);
            transactionRepository.save(transaction);
            throw new UnableCompleteException("Unable to complete operation");
        } else if (transaction.getState().equals(TransactionState.STATE_DONE)) {
            return new PerformTransactionResult(transaction.getId().toString(), transaction.getPerformTime(), transaction.getState().getCode());
        }
        throw new UnableCompleteException("Unable to complete operation");
    }

    @Override
    public CancelTransactionResult cancelTransaction(CancelTransaction cancelTransaction) {
        Optional<OrderTransactionEntity> optional = transactionRepository.findByPaycomId(cancelTransaction.getId());

        if (optional.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }

        OrderTransactionEntity transaction = optional.get();
        if (transaction.getState().equals(TransactionState.STATE_DONE)) {
            if (transaction.getOrder().getDelivered()) {
                throw new UnableCancelTransaction("Unable cancel transaction");
            }
            transaction.setState(TransactionState.STATE_POST_CANCELED);
        } else {
            transaction.setState(TransactionState.STATE_CANCELED);
        }

        transaction.setCancelTime(new Date().getTime());
        transaction.setReason(cancelTransaction.getReason());
        transactionRepository.save(transaction);
        return new CancelTransactionResult(transaction.getId().toString(), transaction.getCancelTime(), transaction.getState().getCode());
    }

    @Override
    public Transactions getStatement(GetStatement getStatement) {
        Optional<List<OrderTransactionEntity>> optional = transactionRepository
                .findByPaycomTimeBetweenAndState(getStatement.getFrom(), getStatement.getTo(), TransactionState.STATE_DONE);

        if (optional.isEmpty()) {
            return new Transactions(new ArrayList<>());
        }

        List<GetStatementResult> collect = optional.get().stream().map(merchantUtil::getStatementResult).collect(Collectors.toList());
        return new Transactions(collect);
    }

    @Override
    public CheckTransactionResult checkTransaction(CheckTransaction checkTransaction) {
        Optional<OrderTransactionEntity> optional = transactionRepository.findByPaycomId(checkTransaction.getId());
        if (optional.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        OrderTransactionEntity transaction = optional.get();
        CheckTransactionResult result = merchantUtil.getCheckTransactionresult(transaction);

        if (transaction.getState() != null) {
            result.setState(transaction.getState().getCode());
        }
        if (transaction.getReason() != null) {
            result.setReason(transaction.getReason().getCode());
        }
        return result;
    }
}
