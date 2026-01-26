package com.team42.churninsight.decision.decisionEngine;

import com.team42.churninsight.decision.decisionEngine.customerCases.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class DecisionEngine {

    public String getRecommendation(DecisionRequest request) {
        return decisionRuleList().stream()
                .filter(r -> r.applies(request))
                .map(DecisionRule::getAction)
                .findFirst()
                .orElse("ERROR_NO_RECOMMENDED_ACTION");
    }

    public String getRecommendationCode(DecisionRequest request) {
        return decisionRuleList().stream()
                .filter(r -> r.applies(request))
                .map(DecisionRule::getActionCode)
                .findFirst()
                .orElse(null);
    }

    private static List<DecisionRule> decisionRuleList() {
        return  List.of(
                new CaseEssentialModerateBuyer.PromoAbuse(),
                new CaseEssentialModerateBuyer.NoRisk(),
                new CaseHighValueDiscountOnline.PromoAbuse(),
                new CaseHighValueDiscountOnline.NoRisk(),
                new CaseInStoreDealHunter.HighChurnPromoAbuse(),
                new CaseInStoreDealHunter.NoRisk(),
                new HighRiskHighValue.InactivityRisk(),
                new HighRiskHighValue.FinancialRisk(),
                new HighRiskHighValue.PromoAbuse(),
                new HighRiskLowValue.InactivityRisk(),
                new HighRiskLowValue.FinancialRisk(),
                new HighRiskLowValue.PromoAbuse(),
                new HighRiskMediumValue.InactivityRisk(),
                new HighRiskMediumValue.FinancialRisk(),
                new HighRiskMediumValue.PromoAbuse(),
                new LowRiskHighValue.AnyFlag(),
                new LowRiskHighValue.NoFLag()
        );
    }
}
