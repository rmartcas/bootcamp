--liquibase formatted sql

--changeset bootcamp team:add_my_user
--comment: Incluir mi username para poder entrar en la app
INSERT INTO core_users(NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('pit jc', 'pitter', 'pedrojc@outlook.es', (SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'));
