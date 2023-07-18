--liquibase formatted sql

--changeset core-config team:core_initial_data
--comment: Initial data for core
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('CORE', 'Permite el acceso a funcionalidades core');
INSERT INTO core_profiles(NAME, DESCRIPTION) VALUES ('Administrador', 'Perfil para administrar los datos maestros de la aplicación');
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES (currval('sq_core_profiles'), currval('sq_core_authorities'));

INSERT INTO core_mappings(PATTERN, POSITION) VALUES ('/', 99);
INSERT INTO core_mappings(PATTERN, POSITION) VALUES ('/api/core/**', 99);

INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings') -1, currval('sq_core_authorities'));
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings'), currval('sq_core_authorities'));

insert into core_menus(title, link, icon, enabled, position, parent_menu_id) values ('menu.administration', 'administration', 'cogs', true, 1, null);