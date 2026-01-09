package com.team42.churninsight.prediction.client;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.team42.churninsight.common.exception.ModelUnavailableException;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("age", request.age());

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
}
