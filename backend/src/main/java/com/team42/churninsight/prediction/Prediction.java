package com.team42.churninsight.prediction;

import com.team42.churninsight.customer.entity.Customer;
import com.team42.churninsight.prediction.enums.Churn;
import com.team42.churninsight.risk.entity.RiskFlag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "predictions",  indexes = {
        @Index(name = "idx_customer_date", columnList = "customer_id, prediction_date DESC")
})
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RiskFlag> riskFlags = new HashSet<>();

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

    public static Prediction create ( Customer customer, Double probabilityChurn, String recommendedAction){
        Prediction newPrediction = new Prediction();
        newPrediction.setCustomer(customer);
        newPrediction.setProbabilityChurn(probabilityChurn);
        newPrediction.calculateChurnStatus();
        newPrediction.setRecommendedAction(recommendedAction);
        newPrediction.setCreatedAt(LocalDateTime.now());
        return newPrediction;
    }
}
