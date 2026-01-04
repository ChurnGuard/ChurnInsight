package com.team42.churninsight.infrastructure.exception;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String message) {
        super(message);
    }
}
