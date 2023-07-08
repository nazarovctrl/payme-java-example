package com.example.paymejava.entity;

import com.example.paymejava.enums.OrderCancelReason;
import com.example.paymejava.enums.TransactionState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Entity
@Table(name = "order_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String paycomId;
    @Column
    private long paycomTime;
    @Column
    @CreationTimestamp
    private long createTime;
    @Column
    private long performTime;
    @Column
    private long cancelTime;
    @Column
    private OrderCancelReason reason;
    @Builder.Default
    @Column(nullable = false)
    private TransactionState state = TransactionState.STATE_IN_PROGRESS;
    @OneToOne
    private OrderEntity order;
}
