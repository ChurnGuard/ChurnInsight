package com.team42.churninsight.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.economic.ValueCustomer;

import java.math.BigDecimal;

public record CriticalCustomerResponse(
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("probability_churn") Double probabilityChurn,
        @JsonProperty("economic_value") ValueCustomer economicValue,
        @JsonProperty("priority_score") BigDecimal priorityScore,
        @JsonProperty("recommended_action") String recommendedAction
) {}
