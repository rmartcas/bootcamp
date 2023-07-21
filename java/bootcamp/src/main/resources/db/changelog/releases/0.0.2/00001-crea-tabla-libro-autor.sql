--liquibase formatted sql

--changeset core-config team:crear_tabla_autor_y_libro
--comment: Initial data for users module

 
CREATE SEQUENCE sq_libros
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

 

CREATE SEQUENCE sq_autores
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE autor (

    id_autor numeric(5,0) PRIMARY KEY DEFAULT nextval('sq_autores'::regclass),
    nombre character varying(250) NOT NULL

);

CREATE TABLE libro (

    id_libro  numeric(5,0) NOT NULL DEFAULT nextval('sq_libros'::regclass),
    titulo character varying(250) NOT NULL,
    id_autor numeric(5,0) NOT NULL,
    CONSTRAINT id_autor_fkey FOREIGN KEY (id_autor)
    REFERENCES autor (id_autor)

);