package com.team42.churninsight.infrastructure.exception;

public class ModelUnavailableException extends RuntimeException {
    public ModelUnavailableException(String message) {
        super(message);
    }
}
