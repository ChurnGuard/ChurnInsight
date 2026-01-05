package com.team42.churninsight.common.exception;

public class PredictionFailedException extends RuntimeException {
    public PredictionFailedException(String message) {
        super(message);
    }
}
