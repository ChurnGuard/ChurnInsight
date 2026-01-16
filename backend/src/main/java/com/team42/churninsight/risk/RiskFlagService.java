package com.team42.churninsight.risk;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.risk.entity.RiskFlag;
import com.team42.churninsight.risk.rules.RiskRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskFlagService {

    private final List<RiskRule> rules;

    public List<RiskFlag> evaluateFlags(PredictionRequest request, Long predictionId){
        return rules.stream().filter(rule -> rule.evaluate(request))
                .map(riskRule -> new RiskFlag(predictionId, riskRule.getFlagType())).toList();
    }

}
