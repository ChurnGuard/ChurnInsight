package com.team42.churninsight.prediction.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.prediction.enums.Churn;
import com.team42.churninsight.risk.enums.FlagType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record DetailsPredictionResponse(

        @NotNull
        @JsonProperty("customer_id")
        String customerId,

        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        @JsonProperty("probability_churn")
        BigDecimal probabilityChurn,

        @NotNull
        Churn churn,

        @NotNull
        @JsonProperty("risk_flags")
        Set<FlagType> riskFlags,

        @NotNull
        @JsonProperty("recommended_action")
        String recommendedAction
) {
}
