package com.team42.churninsight.risk.rules;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.risk.enums.FlagType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InactivityRiskRule implements RiskRule{


    private static final BigDecimal MID_FREQUENCY = new BigDecimal(18);
    private static final int P75_DAYS = 492;

    @Override
    public boolean evaluate(PredictionRequest request) {
        return request.daysSinceLastPurchase() >= P75_DAYS && request.purchaseFrequency().compareTo(MID_FREQUENCY) >= 0;
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.INACTIVITY_RISK;
    }
}
