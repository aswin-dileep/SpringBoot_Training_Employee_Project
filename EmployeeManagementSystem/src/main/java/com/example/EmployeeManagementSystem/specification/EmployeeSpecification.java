package com.example.EmployeeManagementSystem.specification;

import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.entity.EmployeeStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

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

    public static Specification<Employee> hasHireDateBetween(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return (root, query, cb) -> {

            if (startDate == null && endDate == null) {
                return null; // ✅ no date filter
            }

            if (startDate != null && endDate != null) {
                return cb.between(
                        root.get("hireDate"),
                        startDate,
                        endDate
                );
            }

            if (startDate != null) {
                return cb.greaterThanOrEqualTo(
                        root.get("hireDate"),
                        startDate
                );
            }

            return cb.lessThanOrEqualTo(
                    root.get("hireDate"),
                    endDate
            );
        };
    }




}
