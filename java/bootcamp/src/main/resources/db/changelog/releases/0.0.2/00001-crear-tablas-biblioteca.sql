--liquibase formatted sql

--changeset core-config team:crear_tablas_biblioteca
--comement: Initial data for users module

CREATE sequence if not exists sq_libros
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;
 
CREATE sequence if not exists sq_autores
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 99999
    CACHE 1;
    
    
CREATE table if not exists autor (
    id_autor numeric(5,0) PRIMARY KEY DEFAULT nextval('sq_autores'::regclass),
    nombre character varying(250) NOT NULL
);
 
CREATE table if not exists libro (
    id_libro  numeric(5,0) NOT NULL DEFAULT nextval('sq_libros'::regclass),
    titulo character varying(250) NOT NULL,
    id_autor numeric(5,0) NOT NULL,
    CONSTRAINT id_autor_fkey FOREIGN KEY (id_autor)
    REFERENCES autor (id_autor)
);