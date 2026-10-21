package com.mx.development.jose.said.internal.course.controller;

import com.mx.development.jose.said.internal.course.model.dto.Course;
import com.mx.development.jose.said.internal.course.model.dto.Enrollment;
import com.mx.development.jose.said.internal.course.usecase.CourseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Until module 3 the API trusts the X-User-Id header to know who is calling.
 * Insecure on purpose: anyone can send the id of somebody else.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/v1")
public class CourseController {

    private final CourseUseCase courseUseCase;

    /** Published courses. */
    @GetMapping("/course")
    public List<Course> listCatalog() {
        return this.courseUseCase.listCatalogUseCase();
    }

    /** The courses the requester is enrolled in. */
    @GetMapping("/course/mine")
    public List<Course> listMine(@RequestHeader("X-User-Id") String requesterId) {
        return this.courseUseCase.listMineUseCase(requesterId);
    }

    /** A published course. */
    @GetMapping("/course/{courseId}")
    public Course getSummary(@PathVariable String courseId) {
        return this.courseUseCase.getSummaryUseCase(courseId);
    }

    @GetMapping("/course/{courseId}/content")
    public Course getContent(@PathVariable String courseId) {
        return this.courseUseCase.getContentUseCase(courseId);
    }

    /** The instructor is always the requester, never a value from the body. */
    @PostMapping("/course")
    public ResponseEntity<Course> create(
        @RequestHeader("X-User-Id") String requesterId,
        @RequestBody Course course
    ) {
        return new ResponseEntity<>(this.courseUseCase.createUseCase(requesterId, course), HttpStatus.CREATED);
    }

    @PutMapping("/course/{courseId}")
    public Course update(@PathVariable String courseId, @RequestBody Course changes) {
        return this.courseUseCase.updateUseCase(courseId, changes);
    }

    /** Enrolls the requester (free in this workshop). */
    @PostMapping("/course/{courseId}/enroll")
    public ResponseEntity<Enrollment> enroll(
        @RequestHeader("X-User-Id") String requesterId,
        @PathVariable String courseId
    ) {
        return new ResponseEntity<>(this.courseUseCase.enrollUseCase(courseId, requesterId), HttpStatus.CREATED);
    }
}
