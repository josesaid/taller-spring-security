package com.mx.development.jose.said.internal.course.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@Entity
@Table(name = "enrollment", uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "student_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String courseId;

    @Column(nullable = false)
    private String studentId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
