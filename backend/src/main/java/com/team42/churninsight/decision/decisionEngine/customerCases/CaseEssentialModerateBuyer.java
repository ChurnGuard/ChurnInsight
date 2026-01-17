package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;

public class CaseEssentialModerateBuyer {

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.ESSENTIAL_MODERATE_BUYER
                    && r.riskFlagList().contains(FlagType.FINANCIAL_RISK);
        }

        @Override
        public String getAction() {
            return "Plan de ahorro programado + recordatorios de reabastecimiento";
        }
    }

    public static class NoRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.ESSENTIAL_MODERATE_BUYER
                    && r.riskFlagList().isEmpty();
        }

        @Override
        public String getAction() {
            return "Mantenimiento suave + newsletter mensual + descuentos estacionales";
        }
    }
}
