package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;

public class CaseInStoreDealHunter {

    public static class HighChurnPromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.IN_STORE_DEAL_HUNTER
                    && r.riskFlagList().contains(FlagType.PROMO_ABUSE)
                    && r.probabilityChurn() > 0.6;
        }

        @Override
        public String getAction() {
            return "Invitación a eventos en tienda + cashback en lugar de descuentos";
        }

        @Override
        public String getActionCode() {
            return "IN_STORE_DEAL_HUNTER_HIGH_CHURN_PROMO_ABUSE";
        }
    }

    public static class NoRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.IN_STORE_DEAL_HUNTER
                    && r.riskFlagList().isEmpty();
        }

        @Override
        public String getAction() {
            return "Programa de lealtad en tienda + descuentos escalonados por visitas";
        }

        @Override
        public String getActionCode() {
            return "IN_STORE_DEAL_HUNTER_NO_RISK";
        }
    }
}
