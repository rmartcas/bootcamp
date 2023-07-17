--liquibase formatted sql

--changeset core-config team:users_schema
--comment: Sequences and tables for users module
CREATE SEQUENCE sq_core_users
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE core_users
(
    USER_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_core_users'::regclass),
    NAME character varying(100) NOT NULL,
    USERNAME character varying(10) NOT NULL,
    EMAIL character varying(100),
	PROFILE_ID numeric(5,0) NOT NULL,
    CONSTRAINT core_users_pkey PRIMARY KEY (USER_ID),
	
	CONSTRAINT core_users_PROFILE_ID_fkey FOREIGN KEY (PROFILE_ID)
        REFERENCES core_profiles (PROFILE_ID) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);
CREATE UNIQUE INDEX UK_USER_USERNAME on core_users (lower(USERNAME));

COMMENT ON TABLE core_users IS 'Tabla de usuarios con acceso a la app';

CREATE SEQUENCE sq_audit_core_users
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;

CREATE TABLE core_users$AUD
(
    AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_users'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    USER_ID numeric(5,0) NULL,
    NAME character varying(30) NULL,
    USERNAME character varying(10) NULL,
    EMAIL character varying(100) NULL,
	PROFILE_ID numeric(5,0) NULL,
    CONSTRAINT core_users_aud_pkey PRIMARY KEY (AUDIT_ID)
);