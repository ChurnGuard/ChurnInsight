package com.team42.churninsight.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductCategory {
    @JsonProperty("Electronics") ELECTRONICS,
    @JsonProperty("Toys") TOYS,
    @JsonProperty("Groceries") GROCERIES,
    @JsonProperty("Home Goods") HOME_GOODS,
    @JsonProperty("Clothing") CLOTHING
}
