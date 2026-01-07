package com.example.DepartmentManagementSystem.services;

import com.example.DepartmentManagementSystem.dto.DepartmentRequestDTO;
import com.example.DepartmentManagementSystem.dto.DepartmentResponseDTO;
import com.example.DepartmentManagementSystem.entity.Department;
import com.example.DepartmentManagementSystem.mapper.DepartmentMapper;
import com.example.DepartmentManagementSystem.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {

        Department department = departmentMapper.toEntity(dto);
        department.setCreatedAt(LocalDateTime.now());

        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponse(saved);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return departmentMapper.toResponse(dept);
    }

    public DepartmentResponseDTO updateDepartment(DepartmentRequestDTO dto,Long id){
        Department department = departmentRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Department doesn't exist."));

        departmentMapper.updateEntityFromDto(department,dto);

        Department updatedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponse(updatedDepartment);

    }

    public String deleteDepartmentById(Long id){
        Department department = departmentRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Department doesn't exist"));

        departmentRepository.delete(department);

        return "Deleted Successfully";
    }

}
