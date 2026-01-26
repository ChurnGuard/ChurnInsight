package com.team42.churninsight.economic;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EconomicService {

    /**
     * Constantes de dominio derivadas del dataset (provistas por Data Analyst).
     * Se asumen fijas para la versión actual del dataset.
     */
    private static final BigDecimal TOTAL_SALES_MIN = new BigDecimal("6889.13");
    private static final BigDecimal TOTAL_SALES_MAX = new BigDecimal("13680.65");

    private static final BigDecimal AVG_PURCHASE_MIN = new BigDecimal("96.17");
    private static final BigDecimal AVG_PURCHASE_MAX = new BigDecimal("190.01");

    private static final BigDecimal TOTAL_TRANSACTIONS_MIN = new BigDecimal("355.00");
    private static final BigDecimal TOTAL_TRANSACTIONS_MAX = new BigDecimal("3672.00");

    /**
     * Umbrales (percentiles) en escala 0–100
     */
    private static final BigDecimal P40 = new BigDecimal("42.44");
    private static final BigDecimal P75 = new BigDecimal("69.78");

    /**
     * Pesos de la fórmula (suman 1.0)
     */
    private static final BigDecimal W_TOTAL_SALES = new BigDecimal("0.5");
    private static final BigDecimal W_AVG_PURCHASE = new BigDecimal("0.3");
    private static final BigDecimal W_TOTAL_TRANSACTIONS = new BigDecimal("0.2");

    private static final int SCALE = 4;

    /**
     * economic_value_score =
     * 100 * (0.5*norm(total_sales) + 0.3*norm(avg_purchase_value) + 0.2*norm(total_transactions))
     *
     * Resultado en escala 0–100 (para comparar con P40/P75 del issue).
     */
    public BigDecimal economicValueScore(BigDecimal totalSales,
                                         BigDecimal avgPurchaseValue,
                                         Integer totalTransactions) {

        BigDecimal nSales = norm(safe(totalSales), TOTAL_SALES_MIN, TOTAL_SALES_MAX);
        BigDecimal nAvg   = norm(safe(avgPurchaseValue), AVG_PURCHASE_MIN, AVG_PURCHASE_MAX);
        BigDecimal nTx    = norm(new BigDecimal(safe(totalTransactions)),
                TOTAL_TRANSACTIONS_MIN, TOTAL_TRANSACTIONS_MAX);

        BigDecimal weighted01 = nSales.multiply(W_TOTAL_SALES)
                .add(nAvg.multiply(W_AVG_PURCHASE))
                .add(nTx.multiply(W_TOTAL_TRANSACTIONS)); // ∈ [0..1]

        return weighted01.setScale(SCALE, RoundingMode.HALF_UP);   // ∈ [0..100]
    }

    /**
     * Categorización:
     * - score >= P75 -> HIGH
     * - P40 <= score < P75 -> MEDIUM
     * - score < P40 -> LOW
     */
    public ValueCustomer categorize(BigDecimal economicValueScore) {
        BigDecimal score = safe(economicValueScore);
        if (score.compareTo(P75) >= 0) return ValueCustomer.HIGH_VALUE_CUSTOMER;
        if (score.compareTo(P40) >= 0) return ValueCustomer.MEDIUM_VALUE_CUSTOMER;
        return ValueCustomer.LOW_VALUE_CUSTOMER;
    }

    /**
     * priority_score = churn_probability * economic_value_score
     * churn_probability se espera en el rango[0..1]
     */

    /**
     * p = probabilidad de churn, ya validada y “clampada” a [0,1]
    *  s = economic value score, en escala [0,100]
    */

    public BigDecimal priorityScore(BigDecimal churnProbability, BigDecimal economicValueScore) {
        BigDecimal p = clamp01(safe(churnProbability));
        BigDecimal s = safe(economicValueScore);
        return p.multiply(s).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * norm(value, min, max):
     * - si max == min -> 0.0
     * - normalized = (value - min)/(max - min)
     * - clamp que forza al numero al rango [0..1]
     */
    public BigDecimal norm(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (max.compareTo(min) == 0) return BigDecimal.ZERO;

        BigDecimal normalized = value.subtract(min)
                .divide(max.subtract(min), 10, RoundingMode.HALF_UP);

        return clamp01(normalized);
    }

    private BigDecimal clamp01(BigDecimal v) {
        if (v.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        if (v.compareTo(BigDecimal.ONE) > 0) return BigDecimal.ONE;
        return v;
    }
   /**
    Uso de safe para evitar null pointer y
    degradar de forma controlada cuando faltan datos. ya que
    En PredictionRequest:
    * totalSales
    * avgPurchaseValue
    NO están anotados con @NotNull
 */

    private BigDecimal safe(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    private int safe(Integer v) {
        return v == null ? 0 : v;
    }
}
