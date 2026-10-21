-- Workshop seed data (development only): a course platform.
--
-- admin@taller.dev    / admin123 -> ADMIN
-- ines@taller.dev     / user123  -> INSTRUCTOR of "Spring Security desde cero" and of the draft "Kubernetes"
-- ivan@taller.dev     / user123  -> INSTRUCTOR of "Docker para devs" + STUDENT enrolled in Spring Security (two roles)
-- student@taller.dev  / user123  -> STUDENT enrolled in Spring Security
-- newbie@taller.dev   / user123  -> STUDENT without enrollments
-- disabled@taller.dev / user123  -> STUDENT, active = FALSE (cannot log in)
-- Passwords are stored as BCrypt hashes (module 1 explains why)

INSERT INTO app_user (id, email, name, password, active, created_at, updated_at)
VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0001', 'admin@taller.dev', 'Ana Admin',
        '$2a$10$QT1xNkUBEP9kjbGkean./eQxMsKZkRwmrgMirZ/dNsk18OsqSYQCi', TRUE, CURRENT_TIMESTAMP, NULL);

INSERT INTO app_user (id, email, name, password, active, created_at, updated_at)
VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0002', 'student@taller.dev', 'Sofía Student',
        '$2a$10$YrbLeWzlpQLdirx/cytDtOuU8blccc2YRSO5dy66qucN.c9OJbx56', TRUE, CURRENT_TIMESTAMP, NULL);

INSERT INTO app_user (id, email, name, password, active, created_at, updated_at)
VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0003', 'disabled@taller.dev', 'Dani Disabled',
        '$2a$10$YrbLeWzlpQLdirx/cytDtOuU8blccc2YRSO5dy66qucN.c9OJbx56', FALSE, CURRENT_TIMESTAMP, NULL);

INSERT INTO app_user (id, email, name, password, active, created_at, updated_at)
VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0004', 'ines@taller.dev', 'Inés Instructora',
        '$2a$10$YrbLeWzlpQLdirx/cytDtOuU8blccc2YRSO5dy66qucN.c9OJbx56', TRUE, CURRENT_TIMESTAMP, NULL);

INSERT INTO app_user (id, email, name, password, active, created_at, updated_at)
VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0005', 'ivan@taller.dev', 'Iván Instructor',
        '$2a$10$YrbLeWzlpQLdirx/cytDtOuU8blccc2YRSO5dy66qucN.c9OJbx56', TRUE, CURRENT_TIMESTAMP, NULL);

INSERT INTO app_user (id, email, name, password, active, created_at, updated_at)
VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0006', 'newbie@taller.dev', 'Nico Newbie',
        '$2a$10$YrbLeWzlpQLdirx/cytDtOuU8blccc2YRSO5dy66qucN.c9OJbx56', TRUE, CURRENT_TIMESTAMP, NULL);

-- Granted roles: one row per role (app_user_role is the @ElementCollection of AppUserEntity.roles)
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0001', 'ROLE_ADMIN');
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0002', 'ROLE_STUDENT');
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0003', 'ROLE_STUDENT');
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0004', 'ROLE_INSTRUCTOR');
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0005', 'ROLE_INSTRUCTOR');
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0005', 'ROLE_STUDENT');
INSERT INTO app_user_role (user_id, role) VALUES ('2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0006', 'ROLE_STUDENT');

-- Courses: 1 and 2 are published (public catalog), 3 is a draft
INSERT INTO course (id, title, description, price, instructor_id, published, content, created_at, updated_at)
VALUES ('5c0d1e2f-3a4b-4c5d-8e9f-000000000001', 'Spring Security desde cero',
        'Protege una API REST con Spring Security y JWT.',
        599.00, '2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0004', TRUE,
        'Lección 1: la cadena de filtros. Lección 2: BCrypt y UserDetailsService. Lección 3: JWT.',
        CURRENT_TIMESTAMP, NULL);

INSERT INTO course (id, title, description, price, instructor_id, published, content, created_at, updated_at)
VALUES ('5c0d1e2f-3a4b-4c5d-8e9f-000000000002', 'Docker para devs',
        'Contenedores para tu día a día como desarrollador.',
        399.00, '2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0005', TRUE,
        'Lección 1: imágenes y contenedores. Lección 2: Dockerfile. Lección 3: docker compose.',
        CURRENT_TIMESTAMP, NULL);

INSERT INTO course (id, title, description, price, instructor_id, published, content, created_at, updated_at)
VALUES ('5c0d1e2f-3a4b-4c5d-8e9f-000000000003', 'Kubernetes en producción',
        'Borrador: todavía no aparece en el catálogo.',
        899.00, '2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0004', FALSE,
        'Lección 1: pods y deployments (en preparación).',
        CURRENT_TIMESTAMP, NULL);

-- Enrollments: student@ and ivan@ are enrolled in Spring Security
INSERT INTO enrollment (id, course_id, student_id, created_at, updated_at)
VALUES ('7e1a2b3c-4d5e-4f60-9a1b-000000000001', '5c0d1e2f-3a4b-4c5d-8e9f-000000000001', '2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0002', CURRENT_TIMESTAMP, NULL);
INSERT INTO enrollment (id, course_id, student_id, created_at, updated_at)
VALUES ('7e1a2b3c-4d5e-4f60-9a1b-000000000002', '5c0d1e2f-3a4b-4c5d-8e9f-000000000001', '2f1c1a6e-0b4e-4c55-9d0a-6f0e0f6a0005', CURRENT_TIMESTAMP, NULL);
