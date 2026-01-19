package com.team42.churninsight.customer.entity;

import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.prediction.Prediction;
import com.team42.churninsight.profiling.enums.ProfileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "customers", indexes = {
        @Index(name = "idx_priority_score_desc", columnList = "priorityScore DESC"),
    }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    //relacion a prediction
    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    private List<Prediction> predictionList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "economic_value", nullable = false)
    private ValueCustomer economicValue;

    @Column(name = "economic_value_score", nullable = false)
    private BigDecimal economicValueScore;

    @Column(name = "priority_score", nullable = false)
    private BigDecimal priorityScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_profile", nullable = false)
    private ProfileType profileType;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static Customer create(
            String externalId,
            BigDecimal priorityScore,
            ValueCustomer economicValue,
            BigDecimal economicValueScore,
            ProfileType profileType
    ) {
        Customer cc = new Customer();
        cc.setExternalId(externalId);
        cc.setPriorityScore(priorityScore);
        cc.setEconomicValue(economicValue);
        cc.setEconomicValueScore(economicValueScore);
        cc.setProfileType(profileType);
        cc.setCreatedAt(LocalDateTime.now());
        cc.setUpdatedAt(LocalDateTime.now());
        return cc;
    }

    public void update(
            BigDecimal priorityScore,
            ValueCustomer economicValue,
            BigDecimal economicValueScore,
            ProfileType profileType
    ) {
        this.priorityScore = priorityScore;
        this.economicValue = economicValue;
        this.economicValueScore = economicValueScore;
        this.profileType = profileType;
        this.updatedAt = LocalDateTime.now();
    }
}
