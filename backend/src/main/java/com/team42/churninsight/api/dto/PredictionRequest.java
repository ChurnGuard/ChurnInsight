package com.team42.churninsight.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.domain.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PredictionRequest(

        @JsonProperty("customer_id") String customerId,
        Integer age,
        Gender gender,
        @JsonProperty("income_bracket") IncomeBracket incomeBracket,
        @JsonProperty("loyalty_program") Boolean loyaltyProgram,
        @JsonProperty("membership_years") Integer membershipYears,
        @JsonProperty("marital_status") MaritalStatus maritalStatus,
        @JsonProperty("number_of_children") Integer numberOfChildren,
        @JsonProperty("education_level") EducationLevel educationLevel,
        Occupation occupation,
        @JsonProperty("transaction_id") String transactionId,
        //Formato "%Y-%m-%d" => ISO 8601
        @JsonProperty("transaction_date") LocalDate transactionDate,
        @JsonProperty("product_category") ProductCategory productCategory,
        Integer quantity,
        @JsonProperty("unit_price") BigDecimal unitPrice,
        @JsonProperty("avg_purchase") BigDecimal avgPurchase,
        @JsonProperty("purchase_frequency") BigDecimal purchaseFrequency,
        @JsonProperty("last_purchase_date") LocalDate lastPurchaseDate,
        @JsonProperty("avg_discount_used") BigDecimal avgDiscountUsed,
        @JsonProperty("online_purchases") Integer onlinePurchases,
        @JsonProperty("in_store_purchases") Integer inStorePurchases,
        @JsonProperty("total_sales") BigDecimal totalSales,
        @JsonProperty("total_transactions") Integer totalTransactions,
        @JsonProperty("total_items_purchased") Integer totalItemsPurchased,
        @JsonProperty("promotion_type") PromotionType promotionType,
        @JsonProperty("promotion_effectiveness") BigDecimal promotionEffectiveness,
        @JsonProperty("days_since_last_purchase") Integer daysSinceLastPurchase,
        @JsonProperty("promo_flag") Boolean promoFlag,
        @JsonProperty("total_purchases") Integer totalPurchases,
        @JsonProperty("online_ratio") BigDecimal onlineRatio
) {
}
