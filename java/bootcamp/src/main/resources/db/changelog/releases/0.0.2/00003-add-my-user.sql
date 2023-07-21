--liquibase formatted sql

--changeset core-config team:users_initial_data
--comment: Initial data for users module
INSERT INTO core_users(NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('Daniel García Pérez', 'dgarciap01', 'dgarciap01@gmail.com', (SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'));