package com.team42.churninsight.decision.decisionEngine;

import java.util.List;

public interface DecisionRule {
    boolean applies(DecisionRequest request);
    List<String> getActions();
}
