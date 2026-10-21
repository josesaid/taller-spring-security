package com.mx.development.jose.said.internal.course.model.dto;

import lombok.*;

import java.time.LocalDateTime;

/** A student enrolled in a course (enrolling is free in this workshop). */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private String id;
    private String courseId;
    private String studentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
