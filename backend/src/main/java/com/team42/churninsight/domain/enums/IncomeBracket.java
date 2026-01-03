package com.team42.churninsight.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum IncomeBracket {
    @JsonProperty("Low") LOW,
    @JsonProperty("Medium") MEDIUM,
    @JsonProperty("High") HIGH
}
