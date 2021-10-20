CREATE TABLE IF NOT EXISTS car(
    id            SERIAL PRIMARY KEY,
    license_plate VARCHAR(6) NOT NULL,
    make          VARCHAR(255) NOT NULL,
    model         VARCHAR(255) NOT NULL,
    fuel_type     VARCHAR(25) NOT NULL,
    car_status    VARCHAR(25) NOT NULL,
    created_on    TIMESTAMP NOT NULL,
    modified_on   TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS wkst_user(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    birth_date    TIMESTAMP NOT NULL,
    birth_place   VARCHAR(255),
    phone_number  VARCHAR(10),
    active        BOOLEAN NOT NULL,
    created_on    TIMESTAMP NOT NULL,
    modified_on   TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation(
    id                    SERIAL PRIMARY KEY,
    date_from             TIMESTAMP NOT NULL,
    date_to               TIMESTAMP NOT NULL,
    reservation_status    VARCHAR(25) NOT NULL,
    reservation_type      VARCHAR(25) NOT NULL,
    user_id               INTEGER NOT NULL REFERENCES wkst_user(id),
    car_id                INTEGER NOT NULL REFERENCES car(id),
    created_on            TIMESTAMP NOT NULL,
    modified_on           TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS equipment(
    id             SERIAL PRIMARY KEY,
    description    VARCHAR(255) NOT NULL,
    created_on     TIMESTAMP NOT NULL,
    modified_on    TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS car_equipment(
    car_id                INTEGER NOT NULL REFERENCES car(id),
    equipment_id          INTEGER NOT NULL REFERENCES equipment(id)
);