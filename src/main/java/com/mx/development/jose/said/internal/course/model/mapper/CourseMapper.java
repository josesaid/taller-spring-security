package com.mx.development.jose.said.internal.course.model.mapper;

import com.mx.development.jose.said.internal.course.model.dto.Course;
import com.mx.development.jose.said.internal.course.model.dto.Enrollment;
import com.mx.development.jose.said.internal.course.model.entity.CourseEntity;
import com.mx.development.jose.said.internal.course.model.entity.EnrollmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    Course toDto(CourseEntity entity);

    CourseEntity toEntity(Course dto);

    /** Copies the mutable fields of the DTO into a managed entity (only used by the DaoImpl). */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructorId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void merge(Course dto, @MappingTarget CourseEntity entity);

    /** Public view of a course: everything except the paid content. */
    @Mapping(target = "content", ignore = true)
    Course toSummary(Course course);

    Enrollment toDto(EnrollmentEntity entity);

    EnrollmentEntity toEntity(Enrollment dto);
}
