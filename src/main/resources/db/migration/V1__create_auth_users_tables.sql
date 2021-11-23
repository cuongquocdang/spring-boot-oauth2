DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    email              VARCHAR(128) NOT NULL,
    password           VARCHAR(64)  NOT NULL,
    first_name         VARCHAR(45)  NOT NULL,
    last_name          VARCHAR(45)  NOT NULL,
    photos             VARCHAR(64) NULL,
    enabled            BOOLEAN      NOT NULL,
    created_by         BIGINT       NOT NULL,
    created_date       DATETIME     NOT NULL,
    last_modified_by   BIGINT       NOT NULL,
    last_modified_date DATETIME     NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(40)  NOT NULL,
    `description`      VARCHAR(150) NOT NULL,
    created_by         BIGINT       NOT NULL,
    created_date       DATETIME     NOT NULL,
    last_modified_by   BIGINT       NOT NULL,
    last_modified_date DATETIME     NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);

