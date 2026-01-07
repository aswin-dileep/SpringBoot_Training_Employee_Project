package com.example.EmployeeManagementSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;
    private String path;
    private LocalDateTime timestamp;

}
