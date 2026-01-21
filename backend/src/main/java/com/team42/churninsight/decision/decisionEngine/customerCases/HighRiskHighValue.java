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
                    && r.riskFlagList().contains(FlagType.INACTIVITY_RISK);
        }

        @Override
        public String getAction() {
            return "Llamada urgente + beneficio exclusivo + gestor de cuenta dedicado";
        }

        @Override
        public String getActionCode() {
            return "HIGH_RISK_HIGH_VALUE_INACTIVITY_RISK";
        }
    }

    public static class FinancialRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.riskFlagList().contains(FlagType.FINANCIAL_RISK);
        }

        @Override
        public String getAction() {
            return "Reunión con gestor + plan de valor personalizado + descuento estratégico temporal";
        }

        @Override
        public String getActionCode() {
            return "HIGH_RISK_HIGH_VALUE_FINANCIAL_RISK";
        }
    }

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.riskFlagList().contains(FlagType.PROMO_ABUSE);
        }

        @Override
        public String getAction() {
            return "Llamada de retención + migración a programa de fidelización premium sin descuentos";
        }

        @Override
        public String getActionCode() {
            return "HIGH_RISK_HIGH_VALUE_PROMO_ABUSE";
        }
    }
}
