package com.example.DepartmentManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequestDTO {

    @NotBlank(message = "Department name is required")
    private String name;

    private String location;
}