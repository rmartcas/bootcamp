--liquibase formatted sql

--changeset bootcamp team:add-myuser
--comment: Incluir mi username para poder entrar en la app

INSERT INTO core_users(NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('Marcos', 'Marcos', 'marcosgallegogonzalez75@gmail.com', (SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'));
