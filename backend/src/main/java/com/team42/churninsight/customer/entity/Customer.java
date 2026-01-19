package com.team42.churninsight.customer.entity;

import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.profiling.enums.ProfileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "customers",
        uniqueConstraints = @UniqueConstraint(name = "uk_customer_critical_customer_id", columnNames = "customer_id")
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "economic_value", nullable = false)
    private ValueCustomer economicValue;

    @Column(name = "economic_value_score", nullable = false)
    private BigDecimal economicValueScore;

    @Column(name = "priority_score", nullable = false)
    private Double priorityScore;

    @Column(name = "customer_profile", nullable = false)
    private ProfileType profileType;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static Customer from(
            String customerId,
            Double priorityScore,
            Double churnProbability,
            ValueCustomer economicValue,
            String recommendedAction
    ) {
        Customer cc = new Customer();
        cc.setExternalId(customerId);
        cc.setPriorityScore(priorityScore);
        cc.setEconomicValue(economicValue);
        cc.setUpdatedAt(LocalDateTime.now());
        return cc;
    }

    public void update(
            Double priorityScore,
            ValueCustomer economicValue
    ) {
        this.priorityScore = priorityScore;
        this.economicValue = economicValue;
        this.updatedAt = LocalDateTime.now();
    }


}
