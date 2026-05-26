CREATE TABLE devices
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    model_number VARCHAR(256),
    serial_number VARCHAR(256),
    introduction_date DATE,
    location VARCHAR(256),
    status VARCHAR(256) NOT NULL
);

CREATE TABLE reservations
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    device_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    start_date DATE,
    end_date DATE,
    status VARCHAR(256) NOT NULL
);

CREATE TABLE users (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    authority enum('ADMIN', 'USER') NOT NULL
);