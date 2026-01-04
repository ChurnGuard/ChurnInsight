package com.team42.churninsight.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Occupation {
    @JsonProperty("Retired") RETIRED,
    @JsonProperty("Unemployed") UNEMPLOYED,
    @JsonProperty("Student") STUDENT,
    @JsonProperty("Employed") EMPLOYED,
    @JsonProperty("Self-Employed") SELF_EMPLOYED
}
