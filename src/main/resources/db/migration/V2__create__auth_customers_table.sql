DROP TABLE IF EXISTS countries;
CREATE TABLE countries
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(45) NOT NULL,
    code VARCHAR(5)  NOT NULL,
    CONSTRAINT pk_countries PRIMARY KEY (id)
);

DROP TABLE IF EXISTS states;
CREATE TABLE states
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(45) NOT NULL,
    country_id BIGINT NULL,
    CONSTRAINT pk_states PRIMARY KEY (id)
);

ALTER TABLE states
    ADD CONSTRAINT FK_STATES_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES countries (id);

DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    email                VARCHAR(45) NOT NULL,
    password             VARCHAR(64) NOT NULL,
    verification_code    VARCHAR(64) NULL,
    enabled              BOOLEAN     NOT NULL,
    authentication_type  VARCHAR(10) NULL,
    reset_password_token VARCHAR(30) NULL,
    country_id           BIGINT NULL,
    first_name           VARCHAR(45) NOT NULL,
    last_name            VARCHAR(45) NOT NULL,
    phone_number         VARCHAR(15) NOT NULL,
    address_line_1       VARCHAR(64) NOT NULL,
    address_line_2       VARCHAR(64) NULL,
    city                 VARCHAR(45) NOT NULL,
    state                VARCHAR(45) NOT NULL,
    postal_code          VARCHAR(10) NOT NULL,
    created_by           BIGINT      NOT NULL,
    created_date         DATETIME    NOT NULL,
    last_modified_by     BIGINT      NOT NULL,
    last_modified_date   DATETIME    NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_email UNIQUE (email);

ALTER TABLE customers
    ADD CONSTRAINT FK_CUSTOMERS_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES countries (id);