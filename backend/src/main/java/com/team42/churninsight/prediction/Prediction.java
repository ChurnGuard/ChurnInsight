package com.team42.churninsight.prediction;

import com.team42.churninsight.customer.entity.Customer;
import com.team42.churninsight.prediction.enums.Churn;
import com.team42.churninsight.risk.entity.RiskFlag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "predictions")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //relacion a customer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //relacion a riskflags
    @OneToMany(
            mappedBy = "prediction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RiskFlag> riskFlags = new ArrayList<>();

    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "churn_probability")
    private Double probabilityChurn;
    @Enumerated(EnumType.STRING)
    @Column(name = "churn_status")
    private Churn churn;
    @Column(name = "recommended_action")
    private String recommendedAction;
    @Column(name = "prediction_date")
    private LocalDateTime createdAt;

    public void calculateChurnStatus() {
        if (this.probabilityChurn != null && this.probabilityChurn >= 0.5){
            this.churn = Churn.CHURN;
        }else {
            this.churn = Churn.NO_CHURN;
        }
    }

    public static Prediction create ( String transactionId, Double probabilityChurn){
        Prediction newPrediction = new Prediction();

        newPrediction.setTransactionId(transactionId);
        newPrediction.setProbabilityChurn(probabilityChurn);
        newPrediction.calculateChurnStatus();
        newPrediction.setCreatedAt(LocalDateTime.now());
        return newPrediction;
    }
}
