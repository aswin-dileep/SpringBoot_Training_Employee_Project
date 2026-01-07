package com.example.EmployeeManagementSystem.dto;

import com.example.EmployeeManagementSystem.entity.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private double salary;
    private EmployeeStatus status;
    private LocalDate hireDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DepartmentResponseDTO department;
    private Long departmentID;
    private String departmentName;
    private String departmentLocation;

}
