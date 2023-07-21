--liquibase formatted sql

--changeset bootcamp team:add-my-user
--comment: Incluir my username para poder entrar a la bbdd


INSERT INTO core_users(NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('Cristina Rodriguez Gonzalez', 'Criistirg', 'cris.rodriguez.93@gmail.com', (SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'));