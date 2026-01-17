package com.team42.churninsight.decision.decisionEngine;

import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.entity.RiskFlag;
import com.team42.churninsight.risk.enums.FlagType;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public record DecisionRequest(
        Double probabilityChurn,
        ValueCustomer valueCustomer,
        Set<FlagType> riskFlagList,
        ProfileType profileType
) {}
