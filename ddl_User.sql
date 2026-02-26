CREATE TABLE users
(
    user_id    BIGINT       NOT NULL,
    username   VARCHAR(50)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(50)  NULL,
    email      VARCHAR(50)  NULL,
    avatar_url VARCHAR(100) NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_phone UNIQUE (phone);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);