package com.team42.churninsight.decision.decisionEngine;

import com.team42.churninsight.prediction.enums.ValueCustomer;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;

public record DecisionRequest(
        Double probabilityChurn,
        ValueCustomer valueCustomer,
        FlagType flagType,
        ProfileType profileType
) {

}
