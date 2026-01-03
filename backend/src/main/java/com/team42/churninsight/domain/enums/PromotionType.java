package com.team42.churninsight.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PromotionType {

    @JsonProperty("no_promotion") NO_PROMOTION,
    @JsonProperty("20% Off") TWENTY_PERCENT_OFF,
    @JsonProperty("Buy One Get One Free") BUY_ONE_GET_ONE_FREE,
    @JsonProperty("Seasonal Discount") SEASONAL_DISCOUNT
}
