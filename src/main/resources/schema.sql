CREATE TABLE drone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    model VARCHAR(20) NOT NULL,
    weight_limit INT NOT NULL,
    battery FLOAT NOT NULL,
    state VARCHAR(20) NOT NULL
);

CREATE TABLE medication (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    weight INT,
    code VARCHAR(255) NOT NULL UNIQUE,
    image VARCHAR(255)
);

CREATE TABLE drone_medications (
    drone_id BIGINT,
    medications_id BIGINT,
    FOREIGN KEY (drone_id) REFERENCES drone(id),
    FOREIGN KEY (medications_id) REFERENCES medication(id)
);