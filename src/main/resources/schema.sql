CREATE TABLE devices
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL,
    model_number VARCHAR(256),
    serial_number VARCHAR(256) UNIQUE,
    introduction_date DATE,
    location VARCHAR(256),
    status VARCHAR(256) NOT NULL
);