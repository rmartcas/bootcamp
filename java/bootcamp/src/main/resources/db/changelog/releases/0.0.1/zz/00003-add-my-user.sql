--liquibase formatted sql
--changeset bootcamp team:add-my-user	
--coment: incluir el username para entrar a la app

INSERT INTO core_users(NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('Jose Angel Cuervo Blanco', 'joseangel157', 'joseangelcuervo802@gmail.com', (SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'));

