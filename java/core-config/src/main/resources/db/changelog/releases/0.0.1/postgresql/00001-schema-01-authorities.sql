--liquibase formatted sql

--changeset core-config team:authorities_schema
--comment: Sequences and tables for authorities module
CREATE SEQUENCE sq_core_authorities
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE core_authorities
(
    AUTHORITY_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_core_authorities'::regclass),
    NAME character varying(100) NOT NULL,
	DESCRIPTION character varying(250),
    CONSTRAINT core_authorities_pkey PRIMARY KEY (AUTHORITY_ID)
);
CREATE UNIQUE INDEX UK_AUTHORITY_NAME on core_authorities (lower(name));

COMMENT ON TABLE core_authorities IS 'Permisos disponibles en la app';

CREATE SEQUENCE sq_audit_core_authorities
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;

CREATE TABLE core_authorities$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_authorities'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    AUTHORITY_ID numeric(5,0),
    NAME character varying(100),
	DESCRIPTION character varying(250),
   	CONSTRAINT core_authorities_aud_pkey PRIMARY KEY (AUDIT_ID)
);
