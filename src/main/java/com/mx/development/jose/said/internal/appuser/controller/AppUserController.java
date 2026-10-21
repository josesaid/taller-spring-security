package com.mx.development.jose.said.internal.appuser.controller;

import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;
import com.mx.development.jose.said.internal.appuser.usecase.AppUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Until module 3 the API trusts the X-User-Id header to know who is calling.
 * Insecure on purpose: anyone can send the id of somebody else.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/v1")
public class AppUserController {

    private final AppUserUseCase appUserUseCase;

    /** Profile of the requester. */
    @GetMapping("/user/me")
    public AppUser getMe(@RequestHeader("X-User-Id") String requesterId) {
        return this.appUserUseCase.getByIdUseCase(requesterId);
    }

    @GetMapping("/user")
    public List<AppUser> listAll() {
        return this.appUserUseCase.listAllUseCase();
    }

    /** Replaces all the roles of the user. */
    @PutMapping("/user/{userId}/roles")
    public AppUser updateRoles(@PathVariable String userId, @RequestBody Set<AppUser.Role> roles) {
        return this.appUserUseCase.updateRolesUseCase(userId, roles);
    }
}
