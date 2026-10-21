package com.mx.development.jose.said.internal.course.usecase;

import com.mx.development.jose.said.internal.common.exception.BadRequestException;
import com.mx.development.jose.said.internal.common.exception.ConflictException;
import com.mx.development.jose.said.internal.common.exception.NotFoundException;
import com.mx.development.jose.said.internal.course.model.dto.Course;
import com.mx.development.jose.said.internal.course.model.dto.Enrollment;
import com.mx.development.jose.said.internal.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Business flow of the course platform: this class only checks the data.
 * Who may call each operation is not checked yet: that is what the workshop adds.
 */
@Service
@RequiredArgsConstructor
public class CourseUseCase {

    private final CourseService courseService;

    public List<Course> listCatalogUseCase() {
        return this.courseService.listPublished();
    }

    public Course getSummaryUseCase(String courseId) {
        Course course = this.courseService.getById(courseId);
        if (course == null || !Objects.equals(course.getPublished(), Boolean.TRUE)) {
            throw new NotFoundException("Course not found");
        }
        return course;
    }

    public Course getContentUseCase(String courseId) {
        Course course = this.courseService.getById(courseId);
        if (course == null) throw new NotFoundException("Course not found");
        return course;
    }

    public List<Course> listMineUseCase(String requesterId) {
        return this.courseService.listByStudentId(requesterId);
    }

    public Course createUseCase(String requesterId, Course course) {
        if (course.getTitle() == null || course.getTitle().isBlank()) {
            throw new BadRequestException("missing_title", "The title is required");
        }
        if (!this.courseService.isValidPrice(course.getPrice())) {
            throw new BadRequestException("invalid_price", "The price must be zero or greater");
        }
        Course newCourse = Course.builder()
            .title(course.getTitle())
            .description(course.getDescription())
            .price(course.getPrice())
            .instructorId(requesterId)
            .published(Objects.equals(course.getPublished(), Boolean.TRUE))
            .content(course.getContent())
            .createdAt(LocalDateTime.now())
            .build();
        this.courseService.create(newCourse);
        return newCourse;
    }

    public Course updateUseCase(String courseId, Course changes) {
        Course course = this.getContentUseCase(courseId);
        if (changes.getPrice() != null && !this.courseService.isValidPrice(changes.getPrice())) {
            throw new BadRequestException("invalid_price", "The price must be zero or greater");
        }
        if (changes.getTitle() != null && !changes.getTitle().isBlank()) course.setTitle(changes.getTitle());
        if (changes.getDescription() != null) course.setDescription(changes.getDescription());
        if (changes.getPrice() != null) course.setPrice(changes.getPrice());
        if (changes.getPublished() != null) course.setPublished(changes.getPublished());
        if (changes.getContent() != null) course.setContent(changes.getContent());
        this.courseService.update(course);
        return course;
    }

    public Enrollment enrollUseCase(String courseId, String requesterId) {
        this.getSummaryUseCase(courseId);
        if (this.courseService.isEnrolled(courseId, requesterId)) {
            throw new ConflictException("already_enrolled", "You are already enrolled in this course");
        }
        Enrollment enrollment = Enrollment.builder()
            .courseId(courseId)
            .studentId(requesterId)
            .createdAt(LocalDateTime.now())
            .build();
        this.courseService.createEnrollment(enrollment);
        return enrollment;
    }
}
