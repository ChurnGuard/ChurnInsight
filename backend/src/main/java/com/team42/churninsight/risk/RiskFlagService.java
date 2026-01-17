package com.team42.churninsight.risk;

import com.team42.churninsight.prediction.api.dto.PredictionRequest;
import com.team42.churninsight.risk.entity.RiskFlag;
import com.team42.churninsight.risk.enums.FlagType;
import com.team42.churninsight.risk.rules.RiskRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskFlagService {

    private final List<RiskRule> rules;

    //La creacion de las entidades RiskFlag se hace fuera del servicio
    public Set<FlagType> evaluateFlags(PredictionRequest request){
        return rules.stream()
                .filter(rule -> rule.evaluate(request))
                .map(RiskRule::getFlagType)
                .collect(Collectors.toSet());
    }

}
