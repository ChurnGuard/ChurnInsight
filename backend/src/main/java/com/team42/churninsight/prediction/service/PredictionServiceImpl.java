/**
 * PredictionServiceImpl
 *
 * Responsabilidad:
 * - Orquestar el caso de uso "predecir churn" a partir de un PredictionRequest.
 * - Aplicar validaciones de negocio que dependen de la combinación de campos
 *   (más allá de Bean Validation en el DTO).
 * - Delegar la inferencia al modelo externo (FastAPI) mediante ChurnModelClient.
 * - Normalizar la salida del modelo al contrato de la API (probability_churn en [0..1]).
 * - Derivar el label (churn = true/false) usando un umbral (threshold) configurable.
 *
 * Alcance actual:
 * - No hay persistencia (H2 pendiente). Se devuelve una respuesta construida en memoria.
 *
 * Notas de diseño:
 * - El Controller debe ser delgado: solo traduce HTTP -> PredictionService.
 * - El Client encapsula detalles HTTP (WebClient, endpoint, retry, timeout).
 * - Este Service no conoce HTTP (ResponseEntity, status codes), solo dominio.
 */

package com.team42.churninsight.prediction.service;

import com.team42.churninsight.common.exception.InvalidPredictionRequestException;
import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.prediction.api.dto.PredictionResponse;
import com.team42.churninsight.prediction.client.ChurnModelClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private static final BigDecimal DEFAULT_THRESHOLD = new BigDecimal("0.50");

    private final ChurnModelClient churnModelClient;


    /**
     * Caso de uso principal.
     * Flujo:
     * 1) Valida reglas de negocio (coherencias entre campos).
     * 2) Llama al modelo vía ChurnModelClient.
     * 3) Convierte la salida del modelo a probabilidad [0..1].
     * 4) Decide churn con threshold.
     * 5) Construye PredictionResponse.
     */

    @Override
    public PredictionResponse predict(PredictionRequest request) {
        validateBusinessRules(request);

        // 1) Llamada al modelo
        BigDecimal probability = churnModelClient.predictChurn(request);

        // 2) Normalización a probabilidad [0..1]
        //BigDecimal probability = normalizeProbability(raw);

        // 3) Decisión (umbral por ahora fijo)
        boolean churn = probability.compareTo(DEFAULT_THRESHOLD) >= 0;

        // 4) Respuesta (sin persistencia por ahora)
        return new PredictionResponse(
                request.customerId(),
                churn,
                probability
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
     * Normaliza el valor devuelto por el modelo a rango [0..1] como BigDecimal.
     * Esto existe porque actualmente el cliente devuelve Long y el contrato de API
     * exige probability_churn en [0..1].
     *
     * Si más adelante FastAPI devuelve BigDecimal directamente, este metodo puede simplificarse.
     */

/*
    private BigDecimal normalizeProbability(Long raw) {
        if (raw == null) {
            throw new InvalidPredictionRequestException("El modelo devolvió una probabilidad nula");
        }

        // 0 ó 1: válido como caso mínimo
        if (raw == 0L || raw == 1L) {
            return BigDecimal.valueOf(raw).setScale(4, RoundingMode.HALF_UP);
        }

        // 0..100: porcentaje entero
        if (raw >= 0L && raw <= 100L) {
            return BigDecimal.valueOf(raw)
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        }

        // Si el modelo devuelve otra escala (ej. 0..1000), aquí no adivinamos.
        throw new InvalidPredictionRequestException("Probabilidad fuera de rango: " + raw);
    }*/
}