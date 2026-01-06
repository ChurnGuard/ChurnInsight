package com.team42.churninsight.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPredictionRequestException extends RuntimeException {
  public InvalidPredictionRequestException(String message) { super(message); }
  public InvalidPredictionRequestException(String message, Throwable cause) { super(message, cause); }
}
