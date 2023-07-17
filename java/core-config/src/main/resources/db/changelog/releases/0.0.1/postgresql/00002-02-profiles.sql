--liquibase formatted sql

--changeset core-config team:profiles_initial_data
--comment: Initial data for profiles module
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('READ_PROFILES', 'Permite la visualización de perfiles');
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('WRITE_PROFILES', 'Permite la edición y creación de perfiles');

INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities') -1);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities'));

INSERT INTO core_mappings(PATTERN) VALUES ('/api/profiles/init');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/profiles/initedit');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/profiles/search');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/profiles/find');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/profiles/**');

INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -4, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -3, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -2, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -1, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings'), currval('sq_core_authorities'));

insert into core_menus(title, link, icon, enabled, position, parent_menu_id) values ('menu.administration.profiles', 'profiles', 'id-card', true, null, (SELECT menu_id FROM core_menus WHERE title = 'menu.administration'));

INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities') -1);
INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities'));