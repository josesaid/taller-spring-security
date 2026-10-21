package com.mx.development.jose.said.internal.appuser.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    private String id;
    private String email;
    private String name;
    private String password;
    private Set<Role> roles;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * A user can hold several roles at once (an instructor can also be a student of another course).
     */
    public enum Role {
        ROLE_ADMIN,
        ROLE_INSTRUCTOR,
        ROLE_STUDENT
    }
}
