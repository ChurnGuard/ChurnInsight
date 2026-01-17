/**
 * PredictionServiceImpl
 *
 * Responsabilidad:
 * - Orquestar el caso de uso "predecir churn" a partir de un PredictionRequest.
 * - Aplicar validaciones de negocio que dependen de la combinación de campos
 *   (más allá de Bean Validation en el DTO).
 * - Delegar la inferencia al modelo externo (FastAPI) mediante ChurnModelClient.
 * - Normalizar la salida del modelo al contrato de la API (probability_churn en [0..1]).
 *   ESTA PARTE DEBERA BORRARSE AHORA LO DECIDIRÁ LA ENTIDAD
 * - Derivar el label (churn = true/false) usando un umbral (threshold) configurable.
 *
 *   Alcance actual: (ESTA PARTE DEBERA BORRARSE FINALIZADA LA PERSISTENCIA)
 *    --- Hay persistencia vía JPA (H2 en dev) ---
 * - No hay persistencia (H2 pendiente). Se devuelve una respuesta construida en memoria.
 *
 * Notas de diseño:
 * - El Controller debe ser delgado: solo traduce HTTP -> PredictionService.
 * - El Client encapsula detalles HTTP (WebClient, endpoint, retry, timeout).
 * - Este Service no conoce HTTP (ResponseEntity, status codes), solo dominio.
 */

/**  2026-01-07
 * CAMBIOS RECIENTES (Persistencia JPA / H2)
 *
 * - El Service actúa como orquestador del caso de uso: valida, invoca el modelo,
 *   construye la entidad Prediction, persiste el resultado y devuelve la respuesta.
 *
 * - La decisión del estado de churn se delega exclusivamente a la entidad Prediction
 *   (single source of truth), evitando duplicar lógica en el Service.
 *
 * - El resultado de la predicción se persiste antes de responder al cliente,
 *   garantizando coherencia entre lo almacenado y lo expuesto por la API.
 *
 * - Prediction representa el modelo de dominio/persistencia, mientras que
 *   PredictionResponse es un DTO propio de la capa API.
 */

package com.team42.churninsight.prediction.service;

import com.team42.churninsight.common.exception.InvalidPredictionRequestException;
import com.team42.churninsight.decision.service.RecommendedActionService;
import com.team42.churninsight.economic.EconomicService;
import com.team42.churninsight.prediction.Prediction;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.prediction.api.dto.PredictionResponse;
import com.team42.churninsight.prediction.client.ChurnModelClient;

import com.team42.churninsight.prediction.enums.Churn;
import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.prediction.repository.PredictionRepository;


import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.profiling.service.ProfileService;
import com.team42.churninsight.risk.RiskFlagService;
import com.team42.churninsight.risk.entity.RiskFlag;
import com.team42.churninsight.risk.enums.FlagType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final ChurnModelClient churnModelClient;
    private final PredictionRepository predictionRepository;
    private final EconomicService economicService;
    private final ProfileService profileService;
    private final RiskFlagService riskFlagService;
    private final RecommendedActionService recommendedActionService;
    private static final int SCALE = 4;


    /**
     * Caso de uso principal.
     * Flujo:
     * 1) Valida reglas de negocio (coherencias entre campos).
     * 2) Llama al modelo vía ChurnModelClient.
     * 3) Convierte la salida del modelo a probabilidad [0..1]. (actualizado:tu client
     *    ya devuelve BigDecimal en [0..1] por ahora)
     * 4) Decide churn con threshold. (ya no; lo decide la entidad)
     * 5) Construye PredictionResponse.
     */

    @Override
    public PredictionResponse predict(PredictionRequest request) {
        validateBusinessRules(request);

        // 1) Llamada al modelo
        BigDecimal rawpProbability = churnModelClient.predictChurn(request);
        BigDecimal probability = normalizeProbability(rawpProbability);

        // 2) Se calcula el ValueCustomer/PriorityScore
        BigDecimal economicValueScore = economicService.economicValueScore(request.totalSales(),request.avgPurchaseValue(),request.totalTransactions());
        ValueCustomer valueCustomer = economicService.categorize(economicValueScore);
        BigDecimal priorityScore = economicService.priorityScore(probability,economicValueScore);
        System.out.println("EconomicValueScore: "+economicValueScore);
        System.out.println("ValueCustomer: "+valueCustomer);
        System.out.println("PriorityScore: "+priorityScore);

        // 3) Calcular el ProfileType
        ProfileType profileType = profileService.identifyProfile(request);
        System.out.println("ProfileType: "+profileType);

        // 4) Calcular las RiskFlags
        Set<FlagType> flags = riskFlagService.evaluateFlags(request);
        System.out.println("RiskFlags: "+flags.toString());

        // 5) Identificar accion recomendada
        String recomendation = recommendedActionService.getRecommendation(probability.doubleValue(), valueCustomer,flags,profileType);


        Prediction entity = Prediction.create(
                request.customerId(),
                request.transactionId(),
                probability.doubleValue()
        );

        Prediction saved = predictionRepository.save(entity);
        //predictionRepository.save(entity);

        boolean churn = entity.getChurn() == Churn.CHURN;
        //boolean churn = entity.getChurn() == Churn.CHURN;

        // 4) Respuesta (necesario para persistencia)
        //var flags = new ArrayList<FlagType>();


        return new PredictionResponse(
                saved.getCustomerId(),    // en vez de request.customerId()

                probability,               // o BigDecimal.valueOf(entity.getProbabilityChurn())
                churn,

                valueCustomer,
                priorityScore,

                flags,

                profileType,

                recomendation
        );
    }

    /**
     * Reglas de negocio transversales al request.
     * Aquí van validaciones que no se pueden expresar con anotaciones del DTO
     * (por ejemplo, relaciones entre fechas).
     */
    private void validateBusinessRules(PredictionRequest r) {
        // A) Coherencia de fechas (si aplica al dominio)
        if (r.lastPurchaseDate() != null && r.transactionDate() != null
                && r.lastPurchaseDate().isAfter(r.transactionDate())) {
            throw new InvalidPredictionRequestException(
                    "last_purchase_date no puede ser posterior a transaction_date"
            );
        }

        // B) Si days_since_last_purchase aparece, que sea consistente (opcional, pero útil)
        if (r.daysSinceLastPurchase() != null && r.lastPurchaseDate() != null && r.transactionDate() != null) {
            long expected = java.time.temporal.ChronoUnit.DAYS.between(r.lastPurchaseDate(), r.transactionDate());
            if (expected < 0) {
                throw new InvalidPredictionRequestException("days_since_last_purchase inconsistente con las fechas");
            }
            // No es “obligatorio estricto” porque podrían venir datos del dataset ya precomputados,
            // pero se podría activar esta validación si quieren consistencia dura.
        }

        // C) Totales no negativos ya están validados con @Min/@DecimalMin,
        // pero aquí se podría validar coherencias del tipo:
        // totalItemsPurchased >= totalTransactions, etc. (si decidieran reglas)
    }


    /**
     * Valida y normaliza la probabilidad de churn devuelta por el modelo.
     * Reglas:
     * - La probabilidad no puede ser null.
     * - Debe estar en el rango [0..1].
     * - Se normaliza la escala para mantener consistencia numérica.
     * Si el modelo devuelve un valor fuera de rango o inválido,
     * se lanza una excepción para proteger la integridad del dominio.
     */

    private BigDecimal normalizeProbability(BigDecimal raw) {
        if (raw == null) {
            throw new InvalidPredictionRequestException("El modelo devolvió una probabilidad nula");
        }
        if (raw.compareTo(BigDecimal.ZERO) < 0 || raw.compareTo(BigDecimal.ONE) > 0) {
            throw new InvalidPredictionRequestException("Probabilidad fuera de rango [0,1]: " + raw);
        }
        return raw.setScale(SCALE, RoundingMode.HALF_UP);
    }


}