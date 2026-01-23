package com.team42.churninsight.decision.service;

import com.team42.churninsight.decision.client.RecommendationEvent;
import com.team42.churninsight.decision.decisionEngine.DecisionEngine;
import com.team42.churninsight.decision.decisionEngine.DecisionRequest;
import com.team42.churninsight.economic.ValueCustomer;
import com.team42.churninsight.profiling.enums.ProfileType;
import com.team42.churninsight.risk.enums.FlagType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class RecommendedActionService {

    private final DecisionEngine decisionEngine;
    private final ApplicationEventPublisher eventPublisher;

    public RecommendedActionService(DecisionEngine decisionEngine, ApplicationEventPublisher eventPublisher) {
        this.decisionEngine = decisionEngine;
        this.eventPublisher = eventPublisher;
    }

    public String getRecommendation(
            Double probabilityChurn,
            ValueCustomer valueCustomer,
            Set<FlagType> riskFlag,
            ProfileType profileType) {

        DecisionRequest request = new DecisionRequest(
                probabilityChurn, valueCustomer, riskFlag, profileType
        );

        String actionCode = decisionEngine.getRecommendationCode(request);
        String actionDescription = decisionEngine.getRecommendation(request);

        AtomicBoolean emailWasSent = new AtomicBoolean(false);

        if(actionCode != null) {
            eventPublisher.publishEvent(new RecommendationEvent(actionCode, emailWasSent));
        }

        if(emailWasSent.get()) {
            return actionDescription + ". CORREO ENVIADO.";
        }
        return actionDescription;
    }

}
