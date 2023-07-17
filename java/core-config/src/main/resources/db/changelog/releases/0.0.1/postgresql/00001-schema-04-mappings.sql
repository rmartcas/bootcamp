--liquibase formatted sql

--changeset core-config team:mappings_schema
--comment: Sequences and tables for mappings module
CREATE SEQUENCE sq_core_mappings
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE core_mappings
(
    MAPPING_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_core_mappings'::regclass),
    PATTERN character varying(250) NOT NULL,
    POSITION numeric(2,0),
    CONSTRAINT core_mappings_pkey PRIMARY KEY (MAPPING_ID)
);

CREATE UNIQUE INDEX UK_MAPPING_PATTERN on core_mappings (lower(pattern));
COMMENT ON TABLE core_mappings IS 'Listado de patrones url a asegurar';

CREATE TABLE core_mapping_authorities
(
    MAPPING_ID numeric(5,0) NOT NULL,
    AUTHORITY_ID numeric(5,0) NOT NULL,
    CONSTRAINT core_mapping_authorities_pkey PRIMARY KEY (MAPPING_ID, AUTHORITY_ID),
    
    CONSTRAINT core_mapping_authorities_MAPPING_ID_fkey FOREIGN KEY (MAPPING_ID)
        REFERENCES core_mappings (MAPPING_ID),
    CONSTRAINT core_mapping_authorities_AUTHORITY_ID_fkey FOREIGN KEY (AUTHORITY_ID)
        REFERENCES core_authorities (AUTHORITY_ID)
);

COMMENT ON TABLE core_mapping_authorities IS 'Listado de patrones url y permisos asociados a cada uno para poder acceder';

CREATE SEQUENCE sq_audit_core_mappings
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;
    
CREATE TABLE core_mappings$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_mappings'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    MAPPING_ID numeric(5,0),
    PATTERN character varying(250),
    POSITION numeric(2,0),
    CONSTRAINT core_mappings_aud_pk PRIMARY KEY (AUDIT_ID)
);

CREATE SEQUENCE sq_audit_core_mapping_authorities
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;
    
CREATE TABLE core_mapping_authorities$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_mapping_authorities'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    MAPPING_ID numeric(5,0),
    AUTHORITY_ID numeric(5,0),
    CONSTRAINT core_ma_aud_pk PRIMARY KEY (AUDIT_ID)
);