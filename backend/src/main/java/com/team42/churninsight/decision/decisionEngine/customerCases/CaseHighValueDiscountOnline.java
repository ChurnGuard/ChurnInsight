package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;

public class CaseHighValueDiscountOnline {

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.HIGH_VALUE_DISCOUNT_ONLINE
                    && r.riskFlagList().contains(FlagType.PROMO_ABUSE);
        }

        @Override
        public String getAction() {
            return "Migración a suscripción premium con descuento fijo mensual";
        }

        @Override
        public String getActionCode() {
            return "HIGH_VALUE_DISCOUNT_ONLINE_PROMO_ABUSE";
        }
    }

    public static class NoRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.HIGH_VALUE_DISCOUNT_ONLINE
                    && r.riskFlagList().isEmpty();
        }

        @Override
        public String getAction() {
            return "Early access online + envío gratuito permanente + recomendaciones IA";
        }

        @Override
        public String getActionCode() {
            return "HIGH_VALUE_DISCOUNT_ONLINE_NO_RISK";
        }
    }
}
