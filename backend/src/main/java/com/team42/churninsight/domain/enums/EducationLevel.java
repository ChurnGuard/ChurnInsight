package com.team42.churninsight.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EducationLevel {
    @JsonProperty("Master's")
    MASTER_S,
    @JsonProperty("Bachelor's")
    BACHELOR_S,
    @JsonProperty("High School")
    HIGH_SCHOOL,
    @JsonProperty("PhD")
    PHD
}
