package com.example.DepartmentManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "departments",
        indexes = {
                @Index(name = "idx_name", columnList = "name")
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private String location;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
