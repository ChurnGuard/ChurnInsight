package com.team42.churninsight.risk.entity;

import com.team42.churninsight.risk.enums.FlagType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "risk_flag")
public class RiskFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "prediction_id")
    private Long predictionId;
    @Column(name = "flag_type")
    private FlagType flagType;


    public RiskFlag(Long predictionId, FlagType flagType) {
        this.predictionId = predictionId;
        this.flagType = flagType;
    }
}
