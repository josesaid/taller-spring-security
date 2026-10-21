package com.mx.development.jose.said.internal.course.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    /** The instructor that owns the course: the only one (besides an ADMIN) that can edit it. */
    private String instructorId;
    /** Only published courses appear in the public catalog. */
    private Boolean published;
    /** The paid material: only enrolled students, the instructor and an ADMIN can read it. */
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
