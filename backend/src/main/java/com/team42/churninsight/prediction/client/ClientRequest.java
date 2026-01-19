package com.team42.churninsight.prediction.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ClientRequest(
        @NotNull(message = "La edad no puede ser nula")
        @Min(value = 18, message = "La edad mínima es 18")
        @Max(value = 120, message = "La edad máxima es 120")
        @JsonProperty("age")
        Integer age,

        @NotNull(message = "Los años de membresía no pueden ser nulos")
        @Min(value = 0, message = "Los años de membresía no pueden ser negativos")
        @JsonProperty("membership_years")
        Integer membershipYears,

        @NotNull(message = "El número de hijos no puede ser nulo")
        @Min(value = 0, message = "El número de hijos no puede ser negativo")
        @JsonProperty("number_of_children")
        Integer numberOfChildren,

        @NotNull(message = "La cantidad no puede ser nula")
        @DecimalMin(value = "0.0", inclusive = false, message = "La cantidad debe ser mayor a 0")
        @JsonProperty("quantity")
        Integer quantity,

        @NotNull(message = "El precio unitario no puede ser nulo")
        @DecimalMin(value = "0.0", message = "El precio unitario no puede ser negativo")
        @JsonProperty("unit_price")
        BigDecimal unitPrice,

        @NotNull(message = "El valor promedio de compra no puede ser nulo")
        @DecimalMin(value = "0.0", message = "El valor promedio de compra no puede ser negativo")
        @JsonProperty("avg_purchase_value")
        BigDecimal avgPurchaseValue,

        @NotNull(message = "La frecuencia de compra no puede ser nula")
        @DecimalMin(value = "0.0", message = "La frecuencia de compra no puede ser negativa")
        @JsonProperty("purchase_frequency")
        BigDecimal purchaseFrequency,

        @NotNull(message = "El descuento promedio utilizado no puede ser nulo")
        @DecimalMin(value = "0.0", message = "El descuento promedio no puede ser negativo")
        @DecimalMax(value = "1.0", message = "El descuento promedio no puede ser mayor a 1")
        @JsonProperty("avg_discount_used")
        BigDecimal avgDiscountUsed,

        @NotNull(message = "Las compras en línea no pueden ser nulas")
        @Min(value = 0, message = "Las compras en línea no pueden ser negativas")
        @JsonProperty("online_purchases")
        Integer onlinePurchases,

        @NotNull(message = "Las compras en tienda no pueden ser nulas")
        @Min(value = 0, message = "Las compras en tienda no pueden ser negativas")
        @JsonProperty("in_store_purchases")
        Integer inStorePurchases,

        @NotNull(message = "El total de transacciones no puede ser nulo")
        @Min(value = 0, message = "El total de transacciones no puede ser negativo")
        @JsonProperty("total_transactions")
        Integer totalTransactions,

        @NotNull(message = "El total de ítems comprados no puede ser nulo")
        @Min(value = 0, message = "El total de ítems comprados no puede ser negativo")
        @JsonProperty("total_items_purchased")
        Integer totalItemsPurchased,

        @NotNull(message = "La efectividad de promoción no puede ser nula")
        @DecimalMin(value = "0.0", message = "La efectividad de promoción no puede ser negativa")
        @DecimalMax(value = "1.0", message = "La efectividad de promoción no puede ser mayor a 1")
        @JsonProperty("promotion_effectiveness")
        BigDecimal promotionEffectiveness,

        @NotNull(message = "Los días desde la última compra no pueden ser nulos")
        @Min(value = 0, message = "Los días desde la última compra no pueden ser negativos")
        @JsonProperty("days_since_last_purchase")
        Integer daysSinceLastPurchase,

        @NotNull(message = "El programa de lealtad no puede ser nulo")
        @JsonProperty("loyalty_program")
        Boolean loyaltyProgram,

        @NotNull(message = "La bandera de promoción no puede ser nula")
        @JsonProperty("promo_flag")
        Boolean promoFlag,

        // Variables excluyentes - Género
        @NotNull(message = "El género femenino no puede ser nulo")
        @JsonProperty("gender_Female")
        Boolean genderFemale,

        @NotNull(message = "El género masculino no puede ser nulo")
        @JsonProperty("gender_Male")
        Boolean genderMale,

        @NotNull(message = "El género otro no puede ser nulo")
        @JsonProperty("gender_Other")
        Boolean genderOther,

        // Variables excluyentes - Nivel de ingresos
        @NotNull(message = "El nivel de ingresos alto no puede ser nulo")
        @JsonProperty("income_bracket_High")
        Boolean incomeBracketHigh,

        @NotNull(message = "El nivel de ingresos bajo no puede ser nulo")
        @JsonProperty("income_bracket_Low")
        Boolean incomeBracketLow,

        @NotNull(message = "El nivel de ingresos medio no puede ser nulo")
        @JsonProperty("income_bracket_Medium")
        Boolean incomeBracketMedium,

        // Variables excluyentes - Estado civil
        @NotNull(message = "El estado civil divorciado no puede ser nulo")
        @JsonProperty("marital_status_Divorced")
        Boolean maritalStatusDivorced,

        @NotNull(message = "El estado civil casado no puede ser nulo")
        @JsonProperty("marital_status_Married")
        Boolean maritalStatusMarried,

        @NotNull(message = "El estado civil soltero no puede ser nulo")
        @JsonProperty("marital_status_Single")
        Boolean maritalStatusSingle,

        // Variables excluyentes - Nivel educativo
        @NotNull(message = "El nivel educativo de bachiller no puede ser nulo")
        @JsonProperty("education_level_Bachelor's")
        Boolean educationLevelBachelors,

        @NotNull(message = "El nivel educativo de secundaria no puede ser nulo")
        @JsonProperty("education_level_High School")
        Boolean educationLevelHighSchool,

        @NotNull(message = "El nivel educativo de maestría no puede ser nulo")
        @JsonProperty("education_level_Master's")
        Boolean educationLevelMasters,

        @NotNull(message = "El nivel educativo de PhD no puede ser nulo")
        @JsonProperty("education_level_PhD")
        Boolean educationLevelPhD,

        // Variables excluyentes - Ocupación
        @NotNull(message = "La ocupación empleado no puede ser nula")
        @JsonProperty("occupation_Employed")
        Boolean occupationEmployed,

        @NotNull(message = "La ocupación retirado no puede ser nula")
        @JsonProperty("occupation_Retired")
        Boolean occupationRetired,

        @NotNull(message = "La ocupación autoempleado no puede ser nula")
        @JsonProperty("occupation_Self-Employed")
        Boolean occupationSelfEmployed,

        @NotNull(message = "La ocupación estudiante no puede ser nula")
        @JsonProperty("occupation_Student")
        Boolean occupationStudent,

        @NotNull(message = "La ocupación desempleado no puede ser nula")
        @JsonProperty("occupation_Unemployed")
        Boolean occupationUnemployed,

        // Variables excluyentes - Categoría de producto
        @NotNull(message = "La categoría de producto belleza no puede ser nula")
        @JsonProperty("product_category_Beauty")
        Boolean productCategoryBeauty,

        @NotNull(message = "La categoría de producto libros no puede ser nula")
        @JsonProperty("product_category_Books")
        Boolean productCategoryBooks,

        @NotNull(message = "La categoría de producto ropa no puede ser nula")
        @JsonProperty("product_category_Clothing")
        Boolean productCategoryClothing,

        @NotNull(message = "La categoría de producto electrónicos no puede ser nula")
        @JsonProperty("product_category_Electronics")
        Boolean productCategoryElectronics,

        @NotNull(message = "La categoría de producto alimentos no puede ser nula")
        @JsonProperty("product_category_Groceries")
        Boolean productCategoryGroceries,

        @NotNull(message = "La categoría de producto hogar no puede ser nula")
        @JsonProperty("product_category_Home")
        Boolean productCategoryHome,

        @NotNull(message = "La categoría de producto artículos para el hogar no puede ser nula")
        @JsonProperty("product_category_Home Goods")
        Boolean productCategoryHomeGoods,

        @NotNull(message = "La categoría de producto deportes no puede ser nula")
        @JsonProperty("product_category_Sports")
        Boolean productCategorySports,

        @NotNull(message = "La categoría de producto juguetes no puede ser nula")
        @JsonProperty("product_category_Toys")
        Boolean productCategoryToys,

        // Variables excluyentes - Tipo de promoción
        @NotNull(message = "El tipo de promoción 20% de descuento no puede ser nulo")
        @JsonProperty("promotion_type_20% Off")
        Boolean promotionType20PercentOff,

        @NotNull(message = "El tipo de promoción BOGO no puede ser nulo")
        @JsonProperty("promotion_type_BOGO")
        Boolean promotionTypeBOGO,

        @NotNull(message = "El tipo de promoción compra uno lleva otro gratis no puede ser nulo")
        @JsonProperty("promotion_type_Buy One Get One Free")
        Boolean promotionTypeBuyOneGetOneFree,

        @NotNull(message = "El tipo de promoción descuento no puede ser nulo")
        @JsonProperty("promotion_type_Discount")
        Boolean promotionTypeDiscount,

        @NotNull(message = "El tipo de promoción descuento estacional no puede ser nulo")
        @JsonProperty("promotion_type_Seasonal Discount")
        Boolean promotionTypeSeasonalDiscount
) {
}
