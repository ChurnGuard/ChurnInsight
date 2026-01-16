package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.prediction.enums.ValueCustomer;
import com.team42.churninsight.risk.enums.FlagType;

import java.util.List;

public class HighRiskMediumValue {

    public static class InactivityRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.MEDIUM_VALUE_CUSTOMER
                    && r.flagType() == FlagType.INACTIVITY_RISK;
        }

        @Override
        public List<String> getActions() {
            return List.of("Email personalizado + cupón de reactivación + recordatorio de beneficios");
        }
    }

    public static class FinancialRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.MEDIUM_VALUE_CUSTOMER
                    && r.flagType() == FlagType.FINANCIAL_RISK;
        }

        @Override
        public List<String> getActions() {
            return List.of("Email automatizado + oferta de financiamiento/cuotas + productos de entrada");
        }
    }

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.MEDIUM_VALUE_CUSTOMER
                    && r.flagType() == FlagType.PROMO_ABUSE;
        }

        @Override
        public List<String> getActions() {
            return List.of("Email con programa de puntos + beneficios no monetarios");
        }
    }
}
