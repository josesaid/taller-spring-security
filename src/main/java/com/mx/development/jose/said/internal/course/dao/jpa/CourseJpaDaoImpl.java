package com.mx.development.jose.said.internal.course.dao.jpa;

import com.mx.development.jose.said.internal.course.dao.ICourseDao;
import com.mx.development.jose.said.internal.course.model.dto.Course;
import com.mx.development.jose.said.internal.course.model.entity.CourseEntity;
import com.mx.development.jose.said.internal.course.model.mapper.CourseMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseJpaDaoImpl implements ICourseDao {

    private final EntityManager entityManager;
    private final CourseMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Course getById(String courseId) {
        if (courseId == null) return null;
        CourseEntity entity = this.entityManager.find(CourseEntity.class, courseId);
        if (entity == null) return null;
        return this.mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> listByPublished(boolean published) {
        return this.entityManager
            .createQuery(
                "SELECT c FROM CourseEntity c WHERE c.published = :published ORDER BY c.title",
                CourseEntity.class
            )
            .setParameter("published", published)
            .getResultList()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> listByIds(List<String> courseIds) {
        if (courseIds.isEmpty()) return List.of();
        return this.entityManager
            .createQuery("SELECT c FROM CourseEntity c WHERE c.id IN :courseIds ORDER BY c.title", CourseEntity.class)
            .setParameter("courseIds", courseIds)
            .getResultList()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    @Override
    @Transactional
    public void create(Course course) {
        CourseEntity entity = this.mapper.toEntity(course);
        this.entityManager.persist(entity);
        course.setId(entity.getId());
    }

    @Override
    @Transactional
    public void update(Course course) {
        CourseEntity entity = this.entityManager.find(CourseEntity.class, course.getId());
        if (entity == null) return;
        course.setUpdatedAt(LocalDateTime.now());
        // The entity is managed: JPA flushes the changes when the transaction commits
        this.mapper.merge(course, entity);
    }
}
