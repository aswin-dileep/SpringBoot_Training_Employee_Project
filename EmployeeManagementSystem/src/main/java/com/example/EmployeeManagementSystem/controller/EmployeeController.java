package com.example.EmployeeManagementSystem.controller;

import com.example.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.example.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.example.EmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

//    @GetMapping
//    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(){
//
//        return ResponseEntity.ok(employeeService.getAllEmployee());
//    }

    @GetMapping
    public Page<EmployeeResponseDTO> getEmployees(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary
    ) {
        return employeeService.getEmployees(
                page,
                size,
                name,
                status,
                departmentId,
                minSalary,
                maxSalary
        );
    }



    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO dto){

        System.out.println(dto);
        return ResponseEntity.ok(employeeService.addEmployee(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id){
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id,@RequestBody EmployeeRequestDTO dto){
        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployeeById(id,dto);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        String response = employeeService.deleteEmployeeById(id);

        return ResponseEntity.ok(response);
    }



}