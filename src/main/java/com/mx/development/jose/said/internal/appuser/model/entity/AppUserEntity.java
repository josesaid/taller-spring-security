package com.mx.development.jose.said.internal.appuser.model.entity;

import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * JPA entity stored in H2. "user" is a reserved word in H2, that's why the table is "app_user".
 */
@Setter
@Getter
@Builder
@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    @Column(nullable = false)
    private String password;

    /** Stored in its own table (app_user_role): one row per granted role. */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "app_user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<AppUser.Role> roles;

    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
