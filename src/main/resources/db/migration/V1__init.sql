CREATE TABLE account
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    full_name VARCHAR(255)                            NOT NULL,
    email     VARCHAR(255)                            NOT NULL,
    password  VARCHAR(255)                            NOT NULL,
    role      VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE doctor
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    licence_number VARCHAR(255)                            NOT NULL,
    account_id     BIGINT,
    CONSTRAINT pk_doctor PRIMARY KEY (id)
);

CREATE TABLE patient
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    account_id BIGINT,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_email UNIQUE (email);

ALTER TABLE doctor
    ADD CONSTRAINT uc_doctor_licence_number UNIQUE (licence_number);

ALTER TABLE doctor
    ADD CONSTRAINT FK_DOCTOR_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE patient
    ADD CONSTRAINT FK_PATIENT_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);