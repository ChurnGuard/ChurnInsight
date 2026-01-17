package com.team42.churninsight.decision.decisionEngine.customerCases;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.decision.decisionEngine.DecisionRule;
import com.team42.churninsight.economic.ValueCustomer;

public class LowRiskHighValue {

    public static class NoFLag implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() < 0.3
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && r.getFlagTypesList().isEmpty();
        }

        @Override
        public String getAction() {
            return "Programa VIP automático + acceso anticipado + eventos exclusivos";
        }
    }

    public static class AnyFlag implements DecisionRule {

        @Override
        public boolean applies(DecisionRequest r) {
            return r.probabilityChurn() >= 0.3
                    && r.probabilityChurn() <= 0.5
                    && r.valueCustomer() == ValueCustomer.HIGH_VALUE_CUSTOMER
                    && !r.getFlagTypesList().isEmpty();
        }

        @Override
        public String getAction() {
            return "Check-in proactivo + beneficio sorpresa + solicitud de feedback";
        }
    }

}
