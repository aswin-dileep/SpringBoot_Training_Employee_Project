package com.example.EmployeeManagementSystem.specification;

import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.entity.EmployeeStatus;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isBlank()) {
                return null;
            }

            try {
                EmployeeStatus employeeStatus =
                        EmployeeStatus.valueOf(status.toUpperCase());

                return cb.equal(root.get("status"), employeeStatus);

            } catch (IllegalArgumentException e) {
                // Invalid enum value passed
                return cb.disjunction(); // returns no results instead of crashing
            }
        };
    }

    public static Specification<Employee> hasDepartment(Long departmentId) {
        return (root, query, cb) ->
                departmentId == null ? null : cb.equal(root.get("departmentId"), departmentId);
    }

    public static Specification<Employee> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Employee> hasSalaryBetween(
            Double minSalary,
            Double maxSalary
    ) {
        return (root, query, cb) -> {

            if (minSalary == null && maxSalary == null) {
                return null;
            }

            if (minSalary != null && maxSalary != null) {
                return cb.between(root.get("salary"), minSalary, maxSalary);
            }

            if (minSalary != null) {
                return cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
            }

            return cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
        };
    }



}
