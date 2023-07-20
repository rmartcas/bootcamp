--liquibase formatted sql
--changeset bootcamp team:tamaño_username
--coment: incluir el username para entrar a la app


ALTER TABLE core_users 
ALTER COLUMN username TYPE varchar(20) USING username::varchar;