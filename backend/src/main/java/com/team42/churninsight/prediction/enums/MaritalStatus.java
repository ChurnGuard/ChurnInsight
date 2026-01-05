package com.team42.churninsight.prediction.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MaritalStatus {
    @JsonProperty("Divorced") DIVORCED,
    @JsonProperty("Single") SINGLE,
    @JsonProperty("Married") MARRIED
}
