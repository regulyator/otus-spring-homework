DROP TABLE IF EXISTS ICD;
CREATE TABLE ICD
(
    ID        BIGINT PRIMARY KEY,
    rec_code  VARCHAR(255),
    mkb_name  VARCHAR(255),
    mkb_code  VARCHAR(255),
    actual    INT,
    id_parent BIGINT
);