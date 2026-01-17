package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.risk.enums.FlagType;

public class HighRiskHighValue {

    public static class InactivityRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.7
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.getFlagTypesList().contains(FlagType.INACTIVITY_RISK);
        }

        @Override
        public String getAction() {
            return "Llamada urgente + beneficio exclusivo + gestor de cuenta dedicado";
        }
    }

    public static class FinancialRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.getFlagTypesList().contains(FlagType.FINANCIAL_RISK);
        }

        @Override
        public String getAction() {
            return "Reunión con gestor + plan de valor personalizado + descuento estratégico temporal";
        }
    }

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.getFlagTypesList().contains(FlagType.PROMO_ABUSE);
        }

        @Override
        public String getAction() {
            return "Llamada de retención + migración a programa de fidelización premium sin descuentos";
        }
    }
}
