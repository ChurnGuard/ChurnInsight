package com.team42.churninsight.risk.rules;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.risk.enums.FlagType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PromoAbuseRiskRule implements RiskRule{

    private static final BigDecimal MIN_AVG_DISCOUNT_USED = BigDecimal.valueOf(0.30);
    private static final BigDecimal MAX_PROMO_EFFECTIVENESS = BigDecimal.valueOf(0.96);
    private static final boolean PROMO_FLAG_ACTIVE = true;


    @Override
    public boolean evaluate(PredictionRequest request) {
        return request.avgDiscountUsed().compareTo(MIN_AVG_DISCOUNT_USED) >= 0 &&
                request.promotionEffectiveness().compareTo(MAX_PROMO_EFFECTIVENESS) < 0 &&
                request.promoFlag() == PROMO_FLAG_ACTIVE;
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.PROMO_ABUSE;
    }
}
