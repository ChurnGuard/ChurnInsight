package com.team42.churninsight.prediction.client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team42.churninsight.common.exception.ModelUnavailableException;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.prediction.enums.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j

/**
 * Acá va QUÉ HACE el Cliente, no cómo se construye
 * - Endpoint especifico ".uri("/api/v1/predict")"
 * - HTTP Method .post()  .get()
 * - Request / Response  .bodyValue(request)  .bodyToMono(Long.class)
 * - Retry  .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
 * - Errores de dominio "throw new ModelUnavailableException()"
 * - Logging Semantico log.info("prediccion realizada: {}", result)
 * */

@Component
public class ChurnModelClient {

        private final WebClient webClient;



        public BigDecimal predictChurn(PredictionRequest request){
            try{
                //Fast api recibe un Json (clave, valor) como body de request
                var clientRequest = predictionRequestToClientRequest(request);
                Map<String, Object> requestBody = clientRequestToMap(clientRequest);

                ObjectMapper mapper = new ObjectMapper();
                String jsonBody = mapper.writeValueAsString(requestBody);
                log.info("Enviando a FastAPI: {}", jsonBody);

                var probability = webClient.post()
                        .uri("/predict")
                        .bodyValue(requestBody)//envia el objeto como JSON automaticamente
                        .retrieve()
                        .bodyToMono(Double.class)
                        .timeout(Duration.ofSeconds(30))
                        .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))//intenta 3 veces cada 2 segundos
                        .doOnSuccess( r -> log.info("Prediccion realizada: {}",r))
                        .doOnError( er -> log.error("Error en la prediccion: {}",er.getMessage()))
                        .block();
                System.out.println("Respuesta de fast api: "+probability);
                return BigDecimal.valueOf(probability);

            } catch (Exception e) {
                throw new ModelUnavailableException("Modelo predictivo no disponible, intente mas tarde");
            }
        }

        private ClientRequest predictionRequestToClientRequest(PredictionRequest dto){
            return new ClientRequest(
                    dto.age(),
                    dto.membershipYears(),
                    dto.numberOfChildren(),
                    dto.quantity(),
                    dto.unitPrice(),
                    dto.avgPurchaseValue(),
                    dto.purchaseFrequency(),
                    dto.avgDiscountUsed(),
                    dto.onlinePurchases(),
                    dto.inStorePurchases(),
                    dto.totalTransactions(),
                    dto.totalItemsPurchased(),
                    dto.promotionEffectiveness(),
                    dto.daysSinceLastPurchase(),
                    dto.loyaltyProgram(),
                    dto.promoFlag(),
                    // Género - solo uno true
                    dto.gender() == Gender.FEMALE,
                    dto.gender() == Gender.MALE,
                    dto.gender() == Gender.OTHER,
                    // Nivel de ingresos - solo uno true
                    dto.incomeBracket() == IncomeBracket.HIGH,
                    dto.incomeBracket() == IncomeBracket.LOW,
                    dto.incomeBracket() == IncomeBracket.MEDIUM,
                    // Estado civil - solo uno true
                    dto.maritalStatus() == MaritalStatus.DIVORCED,
                    dto.maritalStatus() == MaritalStatus.MARRIED,
                    dto.maritalStatus() == MaritalStatus.SINGLE,
                    // Nivel educativo - solo uno true
                    dto.educationLevel() == EducationLevel.BACHELOR_S,
                    dto.educationLevel() == EducationLevel.HIGH_SCHOOL,
                    dto.educationLevel() == EducationLevel.MASTER_S,
                    dto.educationLevel() == EducationLevel.PHD,
                    // Ocupación - solo uno true
                    dto.occupation() == Occupation.EMPLOYED,
                    dto.occupation() == Occupation.RETIRED,
                    dto.occupation() == Occupation.SELF_EMPLOYED,
                    dto.occupation() == Occupation.STUDENT,
                    dto.occupation() == Occupation.UNEMPLOYED,
                    // Categoría de producto - solo uno true
                    dto.productCategory() == ProductCategory.BEAUTY,
                    dto.productCategory() == ProductCategory.BOOKS,
                    dto.productCategory() == ProductCategory.CLOTHING,
                    dto.productCategory() == ProductCategory.ELECTRONICS,
                    dto.productCategory() == ProductCategory.GROCERIES,
                    dto.productCategory() == ProductCategory.HOME,
                    dto.productCategory() == ProductCategory.HOME_GOODS,
                    dto.productCategory() == ProductCategory.SPORTS,
                    dto.productCategory() == ProductCategory.TOYS,
                    // Tipo de promoción - solo uno true
                    dto.promotionType() == PromotionType.TWENTY_PERCENT_OFF,
                    false,
                    dto.promotionType() == PromotionType.BUY_ONE_GET_ONE_FREE,
                    false,
                    dto.promotionType() == PromotionType.SEASONAL_DISCOUNT
            );
        }

        private Map<String, Object> clientRequestToMap(ClientRequest clientRequest){
            Map<String,Object> map = new HashMap<>();

            // Campos numéricos
            map.put("age", clientRequest.age());
            map.put("membership_years", clientRequest.membershipYears());
            map.put("number_of_children", clientRequest.numberOfChildren());

            map.put("quantity", clientRequest.quantity());
            map.put("unit_price", clientRequest.unitPrice());
            map.put("avg_purchase_value", clientRequest.avgPurchaseValue());
            map.put("purchase_frequency", clientRequest.purchaseFrequency());
            map.put("avg_discount_used", clientRequest.avgDiscountUsed());

            map.put("online_purchases", clientRequest.onlinePurchases());
            map.put("in_store_purchases", clientRequest.inStorePurchases());

            map.put("total_transactions", clientRequest.totalTransactions());
            map.put("total_items_purchased", clientRequest.totalItemsPurchased());
            map.put("promotion_effectiveness", clientRequest.promotionEffectiveness());
            map.put("days_since_last_purchase", clientRequest.daysSinceLastPurchase());

            // Campos booleanos
            map.put("loyalty_program", clientRequest.loyaltyProgram() ? 1 : 0);
            map.put("promo_flag", clientRequest.promoFlag() ? 1 : 0);

            // Variables categóricas (one-hot encoded como 0/1 para que python las entienda)
            map.put("gender_Female", clientRequest.genderFemale() ? 1 : 0);
            map.put("gender_Male", clientRequest.genderMale() ? 1 : 0);
            map.put("gender_Other", clientRequest.genderOther() ? 1 : 0);

            map.put("income_bracket_High", clientRequest.incomeBracketHigh() ? 1 : 0);
            map.put("income_bracket_Low", clientRequest.incomeBracketLow() ? 1 : 0);
            map.put("income_bracket_Medium", clientRequest.incomeBracketMedium() ? 1 : 0);

            map.put("marital_status_Divorced", clientRequest.maritalStatusDivorced() ? 1 : 0);
            map.put("marital_status_Married", clientRequest.maritalStatusMarried() ? 1 : 0);
            map.put("marital_status_Single", clientRequest.maritalStatusSingle() ? 1 : 0);

            map.put("education_level_Bachelors", clientRequest.educationLevelBachelors() ? 1 : 0);
            map.put("education_level_High School", clientRequest.educationLevelHighSchool() ? 1 : 0);
            map.put("education_level_Masters", clientRequest.educationLevelMasters() ? 1 : 0);
            map.put("education_level_PhD", clientRequest.educationLevelPhD() ? 1 : 0);

            map.put("occupation_Employed", clientRequest.occupationEmployed() ? 1 : 0);
            map.put("occupation_Retired", clientRequest.occupationRetired() ? 1 : 0);
            map.put("occupation_Self_Employed", clientRequest.occupationSelfEmployed() ? 1 : 0);
            map.put("occupation_Student", clientRequest.occupationStudent() ? 1 : 0);
            map.put("occupation_Unemployed", clientRequest.occupationUnemployed() ? 1 : 0);

            map.put("product_category_Beauty", clientRequest.productCategoryBeauty() ? 1 : 0);
            map.put("product_category_Books", clientRequest.productCategoryBooks() ? 1 : 0);
            map.put("product_category_Clothing", clientRequest.productCategoryClothing() ? 1 : 0);
            map.put("product_category_Electronics", clientRequest.productCategoryElectronics() ? 1 : 0);
            map.put("product_category_Groceries", clientRequest.productCategoryGroceries() ? 1 : 0);
            map.put("product_category_Home", clientRequest.productCategoryHome() ? 1 : 0);
            map.put("product_category_Home_Goods", clientRequest.productCategoryHomeGoods() ? 1 : 0);
            map.put("product_category_Sports", clientRequest.productCategorySports() ? 1 : 0);
            map.put("product_category_Toys", clientRequest.productCategoryToys() ? 1 : 0);

            map.put("promotion_type_20_Off", clientRequest.promotionType20PercentOff() ? 1 : 0);
            map.put("promotion_type_BOGO", clientRequest.promotionTypeBOGO() ? 1 : 0);
            map.put("promotion_type_Buy_One_Get_One_Free", clientRequest.promotionTypeBuyOneGetOneFree() ? 1 : 0);
            map.put("promotion_type_Discount", clientRequest.promotionTypeDiscount() ? 1 : 0);
            map.put("promotion_type_Seasonal_Discount", clientRequest.promotionTypeSeasonalDiscount() ? 1 : 0);

            return map;
        }
}
