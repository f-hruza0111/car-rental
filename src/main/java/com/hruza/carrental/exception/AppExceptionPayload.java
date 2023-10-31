package com.hruza.carrental.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public record AppExceptionPayload(String message, Throwable throwable, HttpStatus httpStatus, String timestamp) {
}
