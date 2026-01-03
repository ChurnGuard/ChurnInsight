package com.team42.churninsight.exception;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Cuando el modelo o fast api no estan disponibles
    @ExceptionHandler(ModelUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleModelUnavailable(ModelUnavailableException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Model Unavailable Exception",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    //El modelo responde probabilidad fuera de rango ej 1.5 o resultado nulo
    @ExceptionHandler(PredictionFailedException.class)
    public ResponseEntity<ErrorResponse>handlePredictionFailed(PredictionFailedException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                "Prediction Failed Exception",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    //Error al guardar prediccion, BD caída
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponse>handlePersistence(PersistenceException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Persisntence Exception",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    

}
