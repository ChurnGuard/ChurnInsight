package com.team42.churninsight.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team42.churninsight.domain.enums.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PredictionRequest(

        //Identificadores
        @NotBlank
        @Size(min = 3, max = 36)
        @Pattern(
                regexp = "^C[A-Za-z0-9_-]*$",
                message = "El ID debe comenzar con 'C' y solo puede contener letras, números, '-', '_' "
        )
        @JsonProperty("customer_id")
        String customerId,

        @NotBlank
        @Size(min = 3, max = 36)
        @Pattern(
                regexp = "^T[A-Za-z0-9_-]*$",
                message = "El ID debe comenzar con 'T' y solo puede contener letras, números, '-', '_' "
        )
        @JsonProperty("transaction_id")
        String transactionId,

        //Demográficas
        @NotNull
        @Min(0)
        @Max(120)
        Integer age,

        @NotNull
        Gender gender,

        @NotNull
        @JsonProperty("marital_status")
        MaritalStatus maritalStatus,

        @Min(0)
        @Max(15)
        @JsonProperty("number_of_children")
        Integer numberOfChildren,

        //SocioEconómicas
        @NotNull
        @JsonProperty("income_bracket")
        IncomeBracket incomeBracket,

        @NotNull
        @JsonProperty("education_level")
        EducationLevel educationLevel,

        @NotNull
        Occupation occupation,

        //Programa de lealtad / flags
        @NotNull
        @JsonProperty("loyalty_program")
        Boolean loyaltyProgram,

        @NotNull
        @JsonProperty("promo_flag")
        Boolean promoFlag,

        //Fechas - Formato "%Y-%m-%d" => ISO 8601
        @NotNull
        @PastOrPresent
        @JsonProperty("transaction_date")
        LocalDate transactionDate,

        @NotNull
        @PastOrPresent
        @JsonProperty("last_purchase_date")
        LocalDate lastPurchaseDate,

        //Producto / transacción
        @NotNull
        @JsonProperty("product_category")
        ProductCategory productCategory,

        @NotNull
        @Min(1)
        Integer quantity,

        @NotNull
        @DecimalMin(value = "0.0")
        @JsonProperty("unit_price")
        BigDecimal unitPrice,

        //Métricas agregadas
        @JsonProperty("membership_years")
        Integer membershipYears,

        @DecimalMin(value = "0.0")
        @JsonProperty("avg_purchase")
        BigDecimal avgPurchase,

        @DecimalMin(value = "0.0")
        @JsonProperty("purchase_frequency")
        BigDecimal purchaseFrequency,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        @JsonProperty("avg_discount_used")
        BigDecimal avgDiscountUsed,

        @Min(0)
        @JsonProperty("online_purchases")
        Integer onlinePurchases,

        @Min(0)
        @JsonProperty("in_store_purchases")
        Integer inStorePurchases,

        @DecimalMin(value = "0.0")
        @JsonProperty("total_sales")
        BigDecimal totalSales,

        @Min(0)
        @JsonProperty("total_transactions")
        Integer totalTransactions,

        @Min(0)
        @JsonProperty("total_items_purchased")
        Integer totalItemsPurchased,
        
        @JsonProperty("promotion_type")
        PromotionType promotionType,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        @JsonProperty("promotion_effectiveness")
        BigDecimal promotionEffectiveness,

        @Min(0)
        @JsonProperty("days_since_last_purchase")
        Integer daysSinceLastPurchase,

        @Min(0)
        @JsonProperty("total_purchases")
        Integer totalPurchases,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "1.0")
        @JsonProperty("online_ratio")
        BigDecimal onlineRatio
) {
}
