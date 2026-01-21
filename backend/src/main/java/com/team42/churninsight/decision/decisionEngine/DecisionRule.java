package com.team42.churninsight.decision.decisionEngine;

public interface DecisionRule {
    boolean applies(DecisionRequest request);
    String getAction();
    String getActionCode();
}
