package com.example.EmployeeManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;
    private Double salary;
    private EmployeeStatus status;
    private LocalDate hireDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
