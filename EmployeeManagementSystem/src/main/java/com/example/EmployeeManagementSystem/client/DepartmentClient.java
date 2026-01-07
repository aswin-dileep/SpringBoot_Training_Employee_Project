package com.example.EmployeeManagementSystem.client;

import com.example.EmployeeManagementSystem.dto.DepartmentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "department-service",
        url = "http://localhost:8383"
)
public interface DepartmentClient {

    @GetMapping("/departments/{id}")
    DepartmentResponseDTO getDepartmentById(@PathVariable("id") Long id);

}
