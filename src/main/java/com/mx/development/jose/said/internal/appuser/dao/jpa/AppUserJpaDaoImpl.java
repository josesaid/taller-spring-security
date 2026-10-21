package com.mx.development.jose.said.internal.appuser.dao.jpa;

import com.mx.development.jose.said.internal.appuser.dao.IAppUserDao;
import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;
import com.mx.development.jose.said.internal.appuser.model.entity.AppUserEntity;
import com.mx.development.jose.said.internal.appuser.model.mapper.AppUserMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * H2 implementation of the user DAO. This is the only class that changes when the data source changes
 * (in academy-api the same contract is implemented with MongoTemplate).
 */
@Repository
@RequiredArgsConstructor
public class AppUserJpaDaoImpl implements IAppUserDao {

    private final EntityManager entityManager;
    private final AppUserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public AppUser getByEmail(String email) {
        List<AppUserEntity> entities = this.entityManager
            .createQuery("SELECT u FROM AppUserEntity u WHERE u.email = :email", AppUserEntity.class)
            .setParameter("email", email)
            .getResultList();
        if (entities.isEmpty()) return null;
        return this.mapper.toDto(entities.getFirst());
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser getById(String id) {
        if (id == null) return null;
        AppUserEntity entity = this.entityManager.find(AppUserEntity.class, id);
        if (entity == null) return null;
        return this.mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppUser> listAll() {
        return this.entityManager
            .createQuery("SELECT u FROM AppUserEntity u ORDER BY u.createdAt DESC", AppUserEntity.class)
            .getResultList()
            .stream()
            .filter(Objects::nonNull)
            .map(this.mapper::toDto)
            .toList();
    }

    @Override
    @Transactional
    public void create(AppUser appUser) {
        AppUserEntity entity = this.mapper.toEntity(appUser);
        this.entityManager.persist(entity);
        appUser.setId(entity.getId());
    }

    @Override
    @Transactional
    public void update(AppUser appUser) {
        AppUserEntity entity = this.entityManager.find(AppUserEntity.class, appUser.getId());
        if (entity == null) return;
        appUser.setUpdatedAt(LocalDateTime.now());
        // The entity is managed: JPA flushes the changes when the transaction commits
        this.mapper.merge(appUser, entity);
    }
}
