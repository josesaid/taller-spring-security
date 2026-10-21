package com.mx.development.jose.said.internal.appuser.usecase;

import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;
import com.mx.development.jose.said.internal.appuser.model.mapper.AppUserMapper;
import com.mx.development.jose.said.internal.appuser.service.AppUserService;
import com.mx.development.jose.said.internal.common.exception.BadRequestException;
import com.mx.development.jose.said.internal.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserUseCase {

    private final AppUserService appUserService;
    private final AppUserMapper mapper;

    public List<AppUser> listAllUseCase() {
        return this.appUserService.listAll().stream().map(mapper::toDtoSummary).toList();
    }

    public AppUser getByIdUseCase(String userId) {
        AppUser user = this.appUserService.getById(userId);
        if (user == null) throw new NotFoundException("User not found");
        return this.mapper.toDtoSummary(user);
    }

    public AppUser updateRolesUseCase(String userId, Set<AppUser.Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new BadRequestException("empty_roles", "At least one role is required");
        }
        AppUser user = this.appUserService.getById(userId);
        if (user == null) throw new NotFoundException("User not found");
        user.setRoles(roles);
        this.appUserService.update(user);
        return this.mapper.toDtoSummary(user);
    }
}
