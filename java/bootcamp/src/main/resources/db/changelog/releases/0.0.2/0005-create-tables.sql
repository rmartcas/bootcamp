--liquibase formatted sql

--changeset bootcamp team:create-tables
--comment: Sequences and tables for users module


CREATE SEQUENCE sq_autores
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;
    
CREATE SEQUENCE sq_libros
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE AUTORES_GOOD
(
    AUTOR_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_autores'::regclass),
    NAME character varying(100) NOT NULL,
    CONSTRAINT autores_good_pkey PRIMARY KEY (AUTOR_ID)

);

CREATE TABLE LIBROS_GOOD
(
    LIBRO_ID numeric(5,0) NOT NULL DEFAULT nextval('sq_libros'::regclass),
    TITULO character varying(100) NOT NULL,
	AUTOR_ID numeric(5,0) NOT NULL,
    CONSTRAINT libros_good_pkey PRIMARY KEY (LIBRO_ID),
	
	CONSTRAINT autores_good_AUTOR_ID_fkey FOREIGN KEY (AUTOR_ID)
        REFERENCES AUTORES_GOOD (AUTOR_ID) 
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);