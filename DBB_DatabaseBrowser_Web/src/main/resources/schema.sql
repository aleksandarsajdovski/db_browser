DROP TABLE IF EXISTS database_connection_details;
CREATE TABLE database_connection_details
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    hostname        VARCHAR(255) NOT NULL,
    port            VARCHAR(255) NOT NULL,
    database_name   VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    driverClassName VARCHAR(255) NOT NULL
);