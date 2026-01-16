package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.prediction.enums.ValueCustomer;
import com.team42.churninsight.risk.enums.FlagType;

import java.util.List;

public class HighRiskHighValue {

    public static class InactivityRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.7
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.flagType() == FlagType.INACTIVITY_RISK;
        }

        @Override
        public List<String> getActions() {
            return List.of("Llamada urgente + beneficio exclusivo + gestor de cuenta dedicado");
        }
    }

    public static class FinancialRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.flagType() == FlagType.FINANCIAL_RISK;
        }

        @Override
        public List<String> getActions() {
            return List.of("Reunión con gestor + plan de valor personalizado + descuento estratégico temporal");
        }
    }

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.flagType() == FlagType.PROMO_ABUSE;
        }

        @Override
        public List<String> getActions() {
            return List.of("Llamada de retención + migración a programa de fidelización premium sin descuentos");
        }
    }
}
