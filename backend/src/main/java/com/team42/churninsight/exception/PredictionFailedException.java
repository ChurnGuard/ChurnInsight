package com.team42.churninsight.exception;

public class PredictionFailedException extends RuntimeException {
    public PredictionFailedException(String message) {
        super(message);
    }
}
