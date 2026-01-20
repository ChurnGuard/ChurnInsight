package com.team42.churninsight.prediction.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EducationLevel {
    @JsonProperty("Master_s")
    MASTER_S,
    @JsonProperty("Bachelor_s")
    BACHELOR_S,
    @JsonProperty("High_School")
    HIGH_SCHOOL,
    @JsonProperty("PhD")
    PHD
}
