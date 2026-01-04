package com.team42.churninsight.infrastructure.persistence;

import com.team42.churninsight.domain.enums.Churn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "predictions")
public class PredictionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "transaction_id")
    private String transactionId;
    private Double probability;
    @Enumerated(EnumType.STRING)
    private Churn churn;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
