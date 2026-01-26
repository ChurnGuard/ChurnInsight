package com.team42.churninsight.risk.rules;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.risk.enums.FlagType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FinancialRiskRule implements RiskRule{

    private static final BigDecimal MAX_AVG_PURCHASE_VALUE = new BigDecimal(121);
    private static final BigDecimal MIN_AVG_DISCOUNT_USED = BigDecimal.valueOf(0.30);
    private static final BigDecimal MAX_PROMO_EFFECTIVENESS = BigDecimal.valueOf(0.96);


    @Override
    public boolean evaluate(PredictionRequest request) {
        if (request.avgPurchaseValue() == null
                || request.avgDiscountUsed() == null
                || request.promotionEffectiveness() == null) {
            return false;
        }


        return request.avgPurchaseValue().compareTo(MAX_AVG_PURCHASE_VALUE) <= 0 &&
                request.avgDiscountUsed().compareTo(MIN_AVG_DISCOUNT_USED) >= 0 &&
                request.promotionEffectiveness().compareTo(MAX_PROMO_EFFECTIVENESS) < 0;
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.FINANCIAL_RISK;
    }
}
