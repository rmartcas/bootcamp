--liquibase formatted sql

--changeset core-config team:menus_initial_data
--comment: Initial data for menus module
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('READ_MENUS', 'Permite la visualización de la configuración de los menús');
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('WRITE_MENUS', 'Permite la edición y creación de la configuración de los menús');

INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities') -1);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities'));

INSERT INTO core_mappings(PATTERN) VALUES ('/api/menus/init');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/menus/initedit');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/menus/search');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/menus/find');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/menus/**');

INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -4, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -3, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -2, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -1, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings'), currval('sq_core_authorities'));

insert into core_menus(title, link, icon, enabled, position, parent_menu_id) values ('menu.administration.menus', 'menus', 'stream', true, null, (SELECT menu_id FROM core_menus WHERE title = 'menu.administration'));

INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities') -1);
INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities'));
