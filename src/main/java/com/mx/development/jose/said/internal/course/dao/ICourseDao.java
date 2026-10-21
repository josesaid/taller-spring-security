package com.mx.development.jose.said.internal.course.dao;

import com.mx.development.jose.said.internal.course.model.dto.Course;

import java.util.List;

public interface ICourseDao {
    Course getById(String courseId);
    List<Course> listByPublished(boolean published);
    List<Course> listByIds(List<String> courseIds);
    void create(Course course);
    void update(Course course);
}
