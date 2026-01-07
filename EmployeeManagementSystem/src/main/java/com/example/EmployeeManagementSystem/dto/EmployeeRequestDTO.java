package com.example.EmployeeManagementSystem.dto;

import com.example.EmployeeManagementSystem.entity.EmployeeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message="${employee.firstname.requered}" )
    private String firstName;

    @NotBlank(message = "Last Name must not be blank" )
    private String lastName;

    @NotBlank(message = "Email must not be blank" )
    private String email;

    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must be a valid 10-digit Indian mobile number"
    )
    @NotBlank(message = "Phone number must not be blank" )
    private String phoneNumber;

    @NotNull(message = "Department ID must not be blank" )
    private Long departmentId;

    @NotNull(message = "Salary must not be blank" )
    private double salary;

    @NotNull(message = "Status must not be blank" )
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @NotNull(message = "hire Data must not be blank" )
    private LocalDate hireDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
