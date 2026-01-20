package com.example.DepartmentManagementSystem.mapper;

import com.example.DepartmentManagementSystem.dto.DepartmentRequestDTO;
import com.example.DepartmentManagementSystem.dto.DepartmentResponseDTO;
import com.example.DepartmentManagementSystem.entity.Department;
import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequestDTO dto) {
        return Department.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .build();
    }

    public DepartmentResponseDTO toResponse(Department dept) {
        return DepartmentResponseDTO.builder()
                .id(dept.getId())
                .name(dept.getName())
                .location(dept.getLocation())
                .createdAt(dept.getCreatedAt())
                .updatedAt(dept.getUpdatedAt())
                .build();
    }

    public void updateEntityFromDto(Department department,DepartmentRequestDTO dto){

        if (dto.getName()!=null) department.setName(dto.getName());
        if(dto.getLocation() != null) department.setLocation(dto.getLocation());
        department.setUpdatedAt(LocalDateTime.now());
    }

}