package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.prediction.enums.ValueCustomer;
import com.team42.churninsight.risk.enums.FlagType;

public class HighRiskLowValue {

    public static class InactivityRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.LOW_VALUE_CUSTOMER
                    && r.getFlagTypesList().contains(FlagType.INACTIVITY_RISK);
        }

        @Override
        public String getAction() {
            return "Email automatizado de reactivación + descuento moderado";
        }
    }

    public static class FinancialRisk implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.5
                    && r.valueCustomer() == ValueCustomer.LOW_VALUE_CUSTOMER
                    && r.getFlagTypesList().contains(FlagType.FINANCIAL_RISK);
        }

        @Override
        public String getAction() {
            return "Email automático con promo o productos económicos + programa de referidos";
        }
    }

    public static class PromoAbuse implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() > 0.6
                    && r.valueCustomer() == ValueCustomer.LOW_VALUE_CUSTOMER
                    && r.getFlagTypesList().contains(FlagType.PROMO_ABUSE);
        }

        @Override
        public String getAction() {
            return "Email con educación de producto + descuento único limitado";
        }
    }
}
