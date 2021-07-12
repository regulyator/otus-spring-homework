CREATE TABLE ICD
(
    ID        BIGINT PRIMARY KEY,
    rec_code  VARCHAR(255),
    mkb_name  VARCHAR(1000),
    mkb_code  VARCHAR(255),
    actual    INT,
    id_parent BIGINT
);