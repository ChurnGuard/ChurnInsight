package com.team42.churninsight.infrastructure.persistence;

import com.team42.churninsight.domain.enums.Churn;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
public class PredictionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private String customerId;
    private Double probability;
    private Churn churn;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
