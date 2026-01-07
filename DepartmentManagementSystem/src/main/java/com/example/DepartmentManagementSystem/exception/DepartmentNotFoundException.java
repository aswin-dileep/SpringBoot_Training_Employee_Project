package com.example.DepartmentManagementSystem.exception;

public class DepartmentNotFoundException extends RuntimeException{

    public DepartmentNotFoundException(String message){
        super(message);
    }
}
