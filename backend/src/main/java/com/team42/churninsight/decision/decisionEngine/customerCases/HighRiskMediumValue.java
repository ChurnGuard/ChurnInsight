package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.risk.enums.FlagType;

public class HighRiskMediumValue {

    public static class InactivityRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.MEDIUM_VALUE_CUSTOMER
                    && r.riskFlagList().contains(FlagType.INACTIVITY_RISK);
        }

        @Override
        public String getAction() {
            return "Email personalizado + cupón de reactivación + recordatorio de beneficios";
        }
    }

    public static class FinancialRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.MEDIUM_VALUE_CUSTOMER
                    && r.riskFlagList().contains(FlagType.FINANCIAL_RISK);
        }

        @Override
        public String getAction() {
            return "Email automatizado + oferta de financiamiento/cuotas + productos de entrada";
        }
    }

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.MEDIUM_VALUE_CUSTOMER
                    && r.riskFlagList().contains(FlagType.PROMO_ABUSE);
        }

        @Override
        public String getAction() {
            return "Email con programa de puntos + beneficios no monetarios";
        }
    }
}
