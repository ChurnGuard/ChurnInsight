package com.team42.churninsight.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionResponse(
        @JsonProperty("customer_id")
        String customerId,
        Boolean churn,
        @JsonProperty("probability_churn")
        Double probabilityChurn
) {}
