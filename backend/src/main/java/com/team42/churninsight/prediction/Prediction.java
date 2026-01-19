package com.team42.churninsight.prediction;

import com.team42.churninsight.prediction.enums.Churn;
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
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "probability_churn")
    private Double probabilityChurn;
    @Enumerated(EnumType.STRING)
    private Churn churn;
    @Column(name = "recommended_action")
    private String recommendedAction;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void calculateChurnStatus() {
        if (this.probabilityChurn != null && this.probabilityChurn >= 0.5){
            this.churn = Churn.CHURN;
        }else {
            this.churn = Churn.NO_CHURN;
        }
    }

    public static Prediction create (String customerId, String transactionId, Double probabilityChurn){
        Prediction newPrediction = new Prediction();
        newPrediction.setCustomerId(customerId);
        newPrediction.setTransactionId(transactionId);
        newPrediction.setProbabilityChurn(probabilityChurn);
        newPrediction.calculateChurnStatus();
        newPrediction.setCreatedAt(LocalDateTime.now());
        return newPrediction;
    }
}
