package com.hruza.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<Object> handleException(AppException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        AppExceptionPayload payload = new AppExceptionPayload(
                    e.getMessage(),
                    e.getCause(),
                    badRequest,
                    LocalDateTime.now().toString()
            );

        return new ResponseEntity<>(payload,  badRequest);
    }
}
