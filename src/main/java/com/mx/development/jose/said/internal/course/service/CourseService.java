package com.mx.development.jose.said.internal.course.service;

import com.mx.development.jose.said.internal.course.dao.ICourseDao;
import com.mx.development.jose.said.internal.course.dao.IEnrollmentDao;
import com.mx.development.jose.said.internal.course.model.dto.Course;
import com.mx.development.jose.said.internal.course.model.dto.Enrollment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final ICourseDao courseDao;
    private final IEnrollmentDao enrollmentDao;

    public Course getById(String courseId) {
        return this.courseDao.getById(courseId);
    }

    public List<Course> listPublished() {
        return this.courseDao.listByPublished(true);
    }

    public List<Course> listByStudentId(String studentId) {
        List<String> courseIds = this.enrollmentDao.listByStudentId(studentId).stream()
            .map(Enrollment::getCourseId)
            .toList();
        return this.courseDao.listByIds(courseIds);
    }

    public boolean isEnrolled(String courseId, String studentId) {
        return this.enrollmentDao.existsByCourseIdAndStudentId(courseId, studentId);
    }

    public void create(Course course) {
        this.courseDao.create(course);
    }

    public void update(Course course) {
        this.courseDao.update(course);
    }

    public void createEnrollment(Enrollment enrollment) {
        this.enrollmentDao.create(enrollment);
    }

    public boolean isValidPrice(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) >= 0;
    }
}
