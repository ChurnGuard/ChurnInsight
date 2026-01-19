package com.team42.churninsight.prediction.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PromotionType {

    @JsonProperty("No_Promotion") NO_PROMOTION,
    @JsonProperty("Twenty_Percent_Off") TWENTY_PERCENT_OFF,
    @JsonProperty("Buy_One_Get_One_Free") BUY_ONE_GET_ONE_FREE,
    @JsonProperty("Seasonal_Discount") SEASONAL_DISCOUNT
}
