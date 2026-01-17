package com.team42.churninsight.prediction.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record PredictionResponse(
        @NotNull
        @JsonProperty("customer_id")
        String customerId,

        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        @JsonProperty("probability_churn")
        BigDecimal probabilityChurn,

        @NotNull
        Boolean churn,

        @NotNull
        @JsonProperty("economic_value")
        ValueCustomer economicValue,

        @NotNull
        @JsonProperty("priority_score")
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        BigDecimal priorityScore,

        @NotNull
        @JsonProperty("risk_flags")
        List<FlagType> riskFlags,

        @NotNull
        @JsonProperty("customer_profile")
        ProfileType customerProfile,

        @NotNull
        @JsonProperty("recommended_action")
        String recommendedAction
) {}
