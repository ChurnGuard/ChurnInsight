package com.team42.churninsight.decision.client;

import java.util.concurrent.atomic.AtomicBoolean;

public record RecommendationEvent(
        String actionCode,
        AtomicBoolean emailSent
) {
}
