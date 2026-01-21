package com.team42.churninsight.decision.client;

import com.team42.churninsight.decision.decisionEngine.DecisionRequest;

public record RecommendationEvent(
        String actionCode,
        DecisionRequest request
) {
}
