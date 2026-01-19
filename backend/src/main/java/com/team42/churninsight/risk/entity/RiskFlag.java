package com.team42.churninsight.risk.entity;

import com.team42.churninsight.prediction.Prediction;
import com.team42.churninsight.risk.enums.FlagType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "risk_flags")
public class RiskFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prediction_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Prediction prediction;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag_type", nullable = false)
    private FlagType flagType;
    @Column(name = "detected_at", nullable = false)
    private LocalDateTime detectedAt;


    public RiskFlag(Prediction prediction, FlagType flagType) {
        this.prediction = prediction;
        this.flagType = flagType;
        this.detectedAt = LocalDateTime.now();
    }
}
