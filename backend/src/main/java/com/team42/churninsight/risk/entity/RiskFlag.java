package com.team42.churninsight.risk.entity;

import com.team42.churninsight.risk.enums.FlagType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "prediction_id", nullable = false)
    private Long predictionId;
    @Column(name = "flag_type", nullable = false)
    private FlagType flagType;
    @Column(name = "detected_at", nullable = false)
    private LocalDateTime detectedAt;


    public RiskFlag(Long predictionId, FlagType flagType) {
        this.predictionId = predictionId;
        this.flagType = flagType;
        this.detectedAt = LocalDateTime.now();
    }
}
