package com.mx.development.jose.said.internal.appuser.model.mapper;

import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;
import com.mx.development.jose.said.internal.appuser.model.entity.AppUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {
    AppUser toDto(AppUserEntity entity);

    AppUserEntity toEntity(AppUser dto);

    /** Copies the mutable fields of the DTO into a managed entity (only used by the DaoImpl). */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void merge(AppUser dto, @MappingTarget AppUserEntity entity);

    @Named(value = "resume")
    @Mapping(target = "password", ignore = true)
    AppUser toDtoSummary(AppUser entity);
}
