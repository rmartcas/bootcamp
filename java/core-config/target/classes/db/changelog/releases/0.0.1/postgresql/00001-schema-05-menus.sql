--liquibase formatted sql

--changeset core-config team:menus_schema
--comment: Sequences and tables for menus module
CREATE SEQUENCE sq_core_menus
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE core_menus
(
    MENU_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_core_menus'::regclass),
    TITLE character varying(250) NOT NULL,
    LINK character varying(250),
    ICON character varying(100),
    ENABLED boolean DEFAULT true,
    POSITION numeric(2,0),
    PARENT_MENU_ID numeric(5,0),
    CONSTRAINT core_menus_pkey PRIMARY KEY (MENU_ID),
	CONSTRAINT core_menu_parent_id_fkey FOREIGN KEY (PARENT_MENU_ID)
        REFERENCES core_menus (MENU_ID)
);
CREATE UNIQUE INDEX UK_MENU_LINK on core_menus (lower(link));
COMMENT ON TABLE core_menus IS 'Opciones de men� de la aplicaci�n';

CREATE TABLE core_menu_authorities
(
    MENU_ID numeric(5,0) NOT NULL,
    AUTHORITY_ID numeric(5,0) NOT NULL,
    CONSTRAINT core_menu_authorities_pkey PRIMARY KEY (MENU_ID, AUTHORITY_ID),
    
    CONSTRAINT core_menu_authorities_MENU_ID_fkey FOREIGN KEY (MENU_ID)
        REFERENCES core_menus (MENU_ID),
    CONSTRAINT core_menu_authorities_AUTHORITY_ID_fkey FOREIGN KEY (AUTHORITY_ID)
        REFERENCES core_authorities (AUTHORITY_ID)
);

COMMENT ON TABLE core_menu_authorities IS 'Listado de menus y permisos de acceso asociados a cada uno';

CREATE SEQUENCE sq_audit_core_menus
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;
    
CREATE TABLE core_menus$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_menus'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    MENU_ID numeric(5,0),
    TITLE character varying(250),
    LINK character varying(250),
    ICON character varying(100),
    ENABLED boolean,
    POSITION numeric(2,0),
    PARENT_MENU_ID numeric(5,0),
    CONSTRAINT core_menus_aud_pk PRIMARY KEY (AUDIT_ID)
);

CREATE SEQUENCE sq_audit_core_menu_authorities
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999999999
    CACHE 1;
    
CREATE TABLE core_menu_authorities$AUD
(
	AUDIT_ID numeric(10,0) NOT NULL DEFAULT nextval('sq_audit_core_menu_authorities'::regclass),
	REQUEST_ID character varying(36) NOT NULL,
    PAIR_KEY character varying(36) NOT NULL,
    AUDIT_ACTION character varying(10) NOT NULL,
    AUDIT_STEP character varying(10) NOT NULL,
    AUDIT_USER character varying(10) NOT NULL,
    AUDIT_DATE timestamp without time zone NOT NULL,
    MENU_ID numeric(5,0),
    AUTHORITY_ID numeric(5,0),
    CONSTRAINT core_mau_aud_pk PRIMARY KEY (AUDIT_ID)
);