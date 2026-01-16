package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;

import java.util.List;

public class CaseHighValueDiscountOnline {
    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.HIGH_VALUE_DISCOUNT_ONLINE
                    && r.flagType() == FlagType.PROMO_ABUSE;
        }

        @Override
        public List<String> getActions() {
            return List.of("Migración a suscripción premium con descuento fijo mensual");
        }
    }

    public static class NoRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.HIGH_VALUE_DISCOUNT_ONLINE
                    && r.flagType() == null;
        }

        @Override
        public List<String> getActions() {
            return List.of("Early access online + envío gratuito permanente + recomendaciones IA");
        }
    }
}
