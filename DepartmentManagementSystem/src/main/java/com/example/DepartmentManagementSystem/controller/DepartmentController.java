package com.example.DepartmentManagementSystem.controller;

import com.example.DepartmentManagementSystem.dto.DepartmentRequestDTO;
import com.example.DepartmentManagementSystem.dto.DepartmentResponseDTO;
import com.example.DepartmentManagementSystem.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    public DepartmentResponseDTO create(@Valid @RequestBody DepartmentRequestDTO dto) {
        return service.createDepartment(dto);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id,@RequestBody DepartmentRequestDTO dto){
        return ResponseEntity.ok(service.updateDepartment(dto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id){

        return ResponseEntity.ok(service.deleteDepartmentById(id));
    }
}