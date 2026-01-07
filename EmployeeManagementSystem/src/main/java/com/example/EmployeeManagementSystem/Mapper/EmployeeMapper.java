package com.example.EmployeeManagementSystem.Mapper;

import com.example.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.example.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.example.EmployeeManagementSystem.entity.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeMapper {

    public EmployeeResponseDTO toResponseDTO(Employee employee){

        return EmployeeResponseDTO
                .builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .departmentID(employee.getDepartmentId())
                .salary(employee.getSalary())
                .status(employee.getStatus())
                .hireDate(employee.getHireDate())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    public Employee toEntity(EmployeeRequestDTO dto){
        return Employee
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .salary(dto.getSalary())
                .departmentId(dto.getDepartmentId())
                .status(dto.getStatus())
                .hireDate(dto.getHireDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public void updateEntityFromDto(EmployeeRequestDTO dto, Employee entity) {
        if (dto == null) return;

        // Use conditional checks to avoid overwriting data with nulls
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDepartmentId() != null) entity.setDepartmentId(dto.getDepartmentId());
        if (dto.getSalary() != 0) entity.setSalary(dto.getSalary());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getHireDate() != null) entity.setHireDate(dto.getHireDate());

        entity.setUpdatedAt(LocalDateTime.now());

        // Note: Manual timestamps like createdAt are usually handled
        // by JPA/Database, not the DTO update.
    }

}
