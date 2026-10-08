package com.hotel.saas.hotel_saas_backend.common.exception;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>>
    handleBadCredentials(
            BadCredentialsException exception,
            HttpServletRequest request) {

        Map<String, Object> body = Map.of(
                "timestamp",
                LocalDateTime.now().toString(),

                "status",
                401,

                "error",
                "Unauthorized",

                "message",
                "Invalid email or password",

                "path",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }
}
