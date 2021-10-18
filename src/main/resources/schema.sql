CREATE TABLE IF NOT EXISTS `CAR`(
    `id`            BIGINT PRIMARY KEY,
    `license_plate` VARCHAR(6) NOT NULL,
    `make`          VARCHAR(255) NOT NULL,
    `model`         VARCHAR(255) NOT NULL,
    `fuel_type`     VARCHAR(25) NOT NULL,
    `car_status`    VARCHAR(25) NOT NULL,
    `created_on`    TIMESTAMP NOT NULL,
    `modified_on`   TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS `USER`(
    `id`            BIGINT PRIMARY KEY,
    `first_name`    VARCHAR(255) NOT NULL,
    `last_name`     VARCHAR(255) NOT NULL,
    `email`         VARCHAR(255) NOT NULL,
    `birth_date`    DATETIME NOT NULL,
    `birth_place`   VARCHAR(255),
    `phone_number`  VARCHAR(10),
    `active`        BOOLEAN NOT NULL,
    `created_on`    TIMESTAMP NOT NULL,
    `modified_on`   TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS `RESERVATION`(
    `id`                    BIGINT PRIMARY KEY,
    `date_from`             DATETIME NOT NULL,
    `date_to`               DATETIME NOT NULL,
    `reservation_status`    VARCHAR(25) NOT NULL,
    `reservation_type`      VARCHAR(25) NOT NULL,
    `user_id`             BIGINT,
    `car_id`                BIGINT,
    `created_on`            TIMESTAMP NOT NULL,
    `modified_on`           TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS `EQUIPMENT`(
    `id`             BIGINT PRIMARY KEY,
    `description`    VARCHAR(255) NOT NULL,
    `created_on`     TIMESTAMP NOT NULL,
    `modified_on`    TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS `CAR_EQUIPMENT`(
    `car_id`                BIGINT,
    `equipment_id`          BIGINT
);