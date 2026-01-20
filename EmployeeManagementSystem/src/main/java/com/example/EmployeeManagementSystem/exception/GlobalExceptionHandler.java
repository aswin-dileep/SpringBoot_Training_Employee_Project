package com.example.EmployeeManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundException e,
            WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "Resource Not Found",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception e,
            WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
                e.getMessage(),
                "Internal Server Error",
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.computeIfAbsent(err.getField(), k -> new ArrayList<>())
                        .add(err.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }


}
