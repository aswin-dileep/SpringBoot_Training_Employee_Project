package com.example.DepartmentManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(DepartmentNotFoundException e, WebRequest request){
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
}
