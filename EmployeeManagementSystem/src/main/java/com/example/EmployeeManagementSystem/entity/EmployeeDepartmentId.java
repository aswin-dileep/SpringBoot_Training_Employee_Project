package com.example.EmployeeManagementSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDepartmentId implements Serializable {

    @Column(name = "id")
    private Long employeeId;
    @Column(name = "department_id")
    private Long departmentId;

}
