CREATE TABLE account
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    full_name VARCHAR(255)                            NOT NULL,
    email     VARCHAR(255)                            NOT NULL,
    password  VARCHAR(255)                            NOT NULL,
    role_id   BIGINT                                  NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE doctor
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    licence_code VARCHAR(255)                            NOT NULL,
    account_id   BIGINT,
    CONSTRAINT pk_doctor PRIMARY KEY (id)
);

CREATE TABLE patient
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    patient_code VARCHAR(255)                            NOT NULL,
    account_id   BIGINT,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE role
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_role PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_email UNIQUE (email);

ALTER TABLE doctor
    ADD CONSTRAINT uc_doctor_licencecode UNIQUE (licence_code);

ALTER TABLE patient
    ADD CONSTRAINT uc_patient_patientcode UNIQUE (patient_code);

ALTER TABLE role
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE doctor
    ADD CONSTRAINT FK_DOCTOR_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE patient
    ADD CONSTRAINT FK_PATIENT_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);