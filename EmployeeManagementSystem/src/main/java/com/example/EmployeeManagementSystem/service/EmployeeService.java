package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.Mapper.EmployeeMapper;
import com.example.EmployeeManagementSystem.client.DepartmentClient;
import com.example.EmployeeManagementSystem.dto.DepartmentResponseDTO;
import com.example.EmployeeManagementSystem.dto.EmployeeRequestDTO;
import com.example.EmployeeManagementSystem.dto.EmployeeResponseDTO;
import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.exception.NotFoundException;
import com.example.EmployeeManagementSystem.repository.EmployeeRepository;
import com.example.EmployeeManagementSystem.specification.EmployeeSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentClient departmentClient;

    public List<EmployeeResponseDTO> getAllEmployee(){
        List<Employee> employees = employeeRepository.findAll();


        return employees.stream().map(employeeMapper::toResponseDTO)
                .toList();

    }

    public EmployeeResponseDTO addEmployee(EmployeeRequestDTO dto){

        Employee employee = employeeMapper.toEntity(dto);
        employee.setCreatedAt(LocalDateTime.now());
        employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(employee);
    }

    public EmployeeResponseDTO getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Employee does not exist ")
        );

        DepartmentResponseDTO department =
                departmentClient.getDepartmentById(employee.getDepartmentId());

        EmployeeResponseDTO response = employeeMapper.toResponseDTO(employee);

        response.setDepartmentName(department.getName());
        response.setDepartmentLocation(department.getLocation());

        return response;
    }
    @Transactional
    public EmployeeResponseDTO updateEmployeeById(Long id,EmployeeRequestDTO dto){

        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Employee does not exist "));

        employeeMapper.updateEntityFromDto(dto,employee);

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponseDTO(updatedEmployee);
    }

    public String deleteEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new NotFoundException ("Employee does not exist") );

        employeeRepository.delete(employee);

        return "Deleted Successfully";
    }

    public Page<EmployeeResponseDTO> getEmployees(
            int page,
            int size,
            String name,
            String status,
            Long departmentId,
            Double minSalary,
            Double maxSalary
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Specification<Employee> spec = Specification
                .where(EmployeeSpecification.hasName(name))
                .and(EmployeeSpecification.hasStatus(status))
                .and(EmployeeSpecification.hasDepartment(departmentId))
                .and(EmployeeSpecification.hasSalaryBetween(minSalary, maxSalary));

        Page<EmployeeResponseDTO> pageResult =
                employeeRepository.findAll(spec, pageable)
                        .map(employeeMapper::toResponseDTO);

        pageResult.forEach(dto -> {
            if (dto.getDepartmentID() != null) {
                DepartmentResponseDTO dept =
                        departmentClient.getDepartmentById(dto.getDepartmentID());

                dto.setDepartmentName(dept.getName());
                dto.setDepartmentLocation(dept.getLocation());
            }
        });

        return pageResult;
    }



}
