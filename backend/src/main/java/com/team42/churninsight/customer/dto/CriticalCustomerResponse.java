package com.team42.churninsight.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.economic.ValueCustomer;

public record CriticalCustomerResponse(
        @JsonProperty("customer_id") String customerId,
        @JsonProperty("economic_value") ValueCustomer economicValue,
        @JsonProperty("priority_score") Double priorityScore
) {}
