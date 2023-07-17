--liquibase formatted sql

--changeset core-config team:profile_schema
--comment: Sequences and tables for profiles module
CREATE SEQUENCE sq_core_profiles
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE core_profiles
(
    PROFILE_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_core_profiles'::regclass),
    NAME character varying(100) NOT NULL,
	DESCRIPTION character varying(250),
	IS_DEFAULT boolean NOT NULL DEFAULT false,
    CONSTRAINT core_profiles_pkey PRIMARY KEY (PROFILE_ID)
);
CREATE UNIQUE INDEX UK_PROFILE_NAME on core_profiles (lower(name));
CREATE UNIQUE INDEX UK_PROFILE_DEF ON core_profiles (is_default) WHERE (is_default);

COMMENT ON TABLE core_profiles IS 'Perfiles existentes asociables a usuarios';

CREATE TABLE core_profile_authorities
(
    PROFILE_ID numeric(5,0) NOT NULL,
    AUTHORITY_ID numeric(5,0) NOT NULL,
    CONSTRAINT core_profile_authorities_pkey PRIMARY KEY (PROFILE_ID, AUTHORITY_ID),

    CONSTRAINT core_profile_authorities_PROFILE_ID_fkey FOREIGN KEY (PROFILE_ID)
        REFERENCES core_profiles (PROFILE_ID) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT core_profile_authorities_AUTHORITY_ID_fkey FOREIGN KEY (AUTHORITY_ID)
        REFERENCES core_authorities (AUTHORITY_ID) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE core_profile_authorities IS 'Permisos asociados a cada perfil';

CREATE SEQUENCE sq_audit_core_profiles
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;

CREATE TABLE core_profiles$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_profiles'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    PROFILE_ID numeric(5,0),
    NAME character varying(100),
	DESCRIPTION character varying(250),
	IS_DEFAULT boolean,
    CONSTRAINT core_profiles_aud_pkey PRIMARY KEY (AUDIT_ID)
);

CREATE SEQUENCE sq_audit_core_profile_authorities
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;

CREATE TABLE core_profile_authorities$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_profile_authorities'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    PROFILE_ID numeric(5,0),
    AUTHORITY_ID numeric(5,0),
    CONSTRAINT core_profile_authorities_aud_pkey PRIMARY KEY (AUDIT_ID)
);
