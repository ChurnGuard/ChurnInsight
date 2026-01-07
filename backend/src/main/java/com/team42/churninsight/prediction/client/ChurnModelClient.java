package com.team42.churninsight.prediction.client;
import com.team42.churninsight.common.exception.ModelUnavailableException;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;

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
                /*Long probability = webClient.post()
                        .uri("/api/v1/predict")
                        .bodyValue(request)//envia el objeto como JSON automaticamente
                        .retrieve()
                        .bodyToMono(Long.class)
                        .timeout(Duration.ofSeconds(30))
                        .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))//intenta 3 veces cada 2 segundos
                        .doOnSuccess( r -> log.info("Prediccion realizada: {}",r))
                        .doOnError( er -> log.error("Error en la prediccion: {}",er.getMessage()))
                        .block();*/
                return new BigDecimal(0.63);

            } catch (Exception e) {
                throw new ModelUnavailableException("Modelo predictivo no disponible, intente mas tarde");
            }
        }
}
