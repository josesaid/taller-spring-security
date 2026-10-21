package com.mx.development.jose.said.internal.course.dao.jpa;

import com.mx.development.jose.said.internal.course.dao.IEnrollmentDao;
import com.mx.development.jose.said.internal.course.model.dto.Enrollment;
import com.mx.development.jose.said.internal.course.model.entity.EnrollmentEntity;
import com.mx.development.jose.said.internal.course.model.mapper.CourseMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EnrollmentJpaDaoImpl implements IEnrollmentDao {

    private final EntityManager entityManager;
    private final CourseMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCourseIdAndStudentId(String courseId, String studentId) {
        return this.entityManager
            .createQuery(
                "SELECT COUNT(e) > 0 FROM EnrollmentEntity e WHERE e.courseId = :courseId AND e.studentId = :studentId",
                Boolean.class
            )
            .setParameter("courseId", courseId)
            .setParameter("studentId", studentId)
            .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollment> listByStudentId(String studentId) {
        return this.entityManager
            .createQuery("SELECT e FROM EnrollmentEntity e WHERE e.studentId = :studentId", EnrollmentEntity.class)
            .setParameter("studentId", studentId)
            .getResultList()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    @Override
    @Transactional
    public void create(Enrollment enrollment) {
        EnrollmentEntity entity = this.mapper.toEntity(enrollment);
        this.entityManager.persist(entity);
        enrollment.setId(entity.getId());
    }
}
