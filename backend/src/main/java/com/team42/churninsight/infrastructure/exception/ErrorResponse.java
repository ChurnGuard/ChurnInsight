package com.team42.churninsight.infrastructure.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timeStamp
) {
}
