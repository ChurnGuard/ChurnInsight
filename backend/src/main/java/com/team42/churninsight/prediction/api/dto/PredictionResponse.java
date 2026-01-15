package com.team42.churninsight.prediction.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.prediction.enums.ValueCustomer;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PredictionResponse(
        @NotNull
        @JsonProperty("customer_id")
        String customerId,

        @NotNull
        Boolean churn,

        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        @JsonProperty("probability_churn")
        BigDecimal probabilityChurn

        // --- NUEVO --- aqui debe ir la parte de "economic_value_score", "value_score", "priority_score"

) {}
