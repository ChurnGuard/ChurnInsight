package com.team42.churninsight.risk.rules;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.risk.enums.FlagType;

public interface RiskRule {
    boolean evaluate(PredictionRequest request);
    FlagType getFlagType();
}
