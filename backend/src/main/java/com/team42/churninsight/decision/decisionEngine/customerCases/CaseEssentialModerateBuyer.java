package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;

import java.util.List;

public class CaseEssentialModerateBuyer {
    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.ESSENTIAL_MODERATE_BUYER
                    && r.flagType() == FlagType.FINANCIAL_RISK;
        }

        @Override
        public List<String> getActions() {
            return List.of("Plan de ahorro programado + recordatorios de reabastecimiento");
        }
    }

    public static class NoRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.profileType() == ProfileType.ESSENTIAL_MODERATE_BUYER
                    && r.flagType() == null;
        }

        @Override
        public List<String> getActions() {
            return List.of("Mantenimiento suave + newsletter mensual + descuentos estacionales");
        }
    }
}
