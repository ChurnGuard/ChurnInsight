package com.team42.churninsight.decision.service;

import com.team42.churninsight.decision.decisionEngine.DecisionEngine;
import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RecommendedActionService {

    private final DecisionEngine decisionEngine;

    public RecommendedActionService(DecisionEngine decisionEngine) {
        this.decisionEngine = decisionEngine;
    }

    public String getRecommendation(
            Double probabilityChurn,
            ValueCustomer valueCustomer,
            Set<FlagType> riskFlag,
            ProfileType profileType) {

        String recommendedAction= decisionEngine.getRecommendation(new DecisionRequest(probabilityChurn,valueCustomer,riskFlag,profileType));

        if(recommendedAction == null || recommendedAction.isEmpty()){
            recommendedAction = "Sin acción recomendada";
        }
        return recommendedAction;
    }
}
