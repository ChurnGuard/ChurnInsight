package com.team42.churninsight.decision.service;

import com.team42.churninsight.decision.decisionEngine.DecisionEngine;
import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import org.springframework.stereotype.Service;

@Service
public class RecommendedActionService {

    private final DecisionEngine decisionEngine;

    public RecommendedActionService(DecisionEngine decisionEngine) {
        this.decisionEngine = decisionEngine;
    }

    public String getRecommendation(DecisionRequest request) {
        return decisionEngine.getRecommendation(request);
    }
}
