package com.team42.churninsight.domain;


import com.team42.churninsight.domain.enums.Churn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {
    private Long id;
    private String customerId;
    private Double probabilityChurn;
    private Churn churn;
    private LocalDateTime createdAt = LocalDateTime.now();
}

