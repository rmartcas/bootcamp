--liquibase formatted sql

--changeset core-config team:profiles_initial_data_test
--comment: Initial data for profiles module
INSERT INTO core_profiles(PROFILE_ID, NAME, DESCRIPTION, IS_DEFAULT) VALUES (-2, 'Usuario', 'Perfil con acceso de lectura a los recursos de las aplicaciones que tenga asignadas', true);
INSERT INTO core_profiles(PROFILE_ID, NAME, DESCRIPTION) VALUES (-3, 'No profile module access', 'A profile without any authority');
INSERT INTO core_profiles(PROFILE_ID, NAME, DESCRIPTION) VALUES (-4, 'Delete', 'A delete profile');

-- Admin authorities
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (1, (select authority_id from core_authorities where name = 'NOT_USED_AUTHORITY'));

-- User authorities
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 1);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 2);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 4);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 6);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 8);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 10);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-2, 12);

-- No access profile authorities
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (-3, 1);