package com.mx.development.jose.said.internal.course.dao;

import com.mx.development.jose.said.internal.course.model.dto.Enrollment;

import java.util.List;

public interface IEnrollmentDao {
    boolean existsByCourseIdAndStudentId(String courseId, String studentId);
    List<Enrollment> listByStudentId(String studentId);
    void create(Enrollment enrollment);
}
