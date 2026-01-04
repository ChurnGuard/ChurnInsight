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
    private String transactionId;
    private Double probabilityChurn;
    private Churn churn;
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

