--liquibase formatted sql

--changeset bootcamp team:add-my-user-good
--comment: Add new user

INSERT INTO core_users(NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('Alexandra Zhurina', 'alexandrazhurina', 'zhurina.alexandra@gmail.com', (SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'));
