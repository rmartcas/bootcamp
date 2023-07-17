--liquibase formatted sql

--changeset core-config team:authorities_initial_data
--comment: Initial data for authorities module
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('READ_AUTHORITIES', 'Permite la visualización de los distintos permisos');
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('WRITE_AUTHORITIES', 'Permite la edición y creación de permisos');

INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities') -1);
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities'));

INSERT INTO core_mappings(PATTERN) VALUES ('/api/authorities/init');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/authorities/initedit');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/authorities/search');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/authorities/find');
INSERT INTO core_mappings(PATTERN) VALUES ('/api/authorities/**');

INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -4, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -3, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -2, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -1, currval('sq_core_authorities') -1);
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings'), currval('sq_core_authorities'));

insert into core_menus(title, link, icon, enabled, position, parent_menu_id) values ('menu.administration.authorities', 'authorities', 'user-tag', true, null, (SELECT menu_id FROM core_menus WHERE title = 'menu.administration'));

INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities') -1);
INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities'));