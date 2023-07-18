--liquibase formatted sql

--changeset core-config team:audit_initial_data
--comment: Initial data for audit module
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('AUDIT', 'Permite el acceso al módulo de auditorías');
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((SELECT profile_id FROM core_profiles WHERE lower(name) = 'administrador'), currval('sq_core_authorities'));

INSERT INTO core_mappings(PATTERN) VALUES ('/api/audit/**');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES (currval('sq_core_mappings'), currval('sq_core_authorities'));

insert into core_menus(title, link, icon, enabled, position, parent_menu_id) values ('menu.administration.audit', 'audit', 'fingerprint', true, null, (SELECT menu_id FROM core_menus WHERE title = 'menu.administration'));
INSERT INTO core_menu_authorities(menu_id, AUTHORITY_ID) VALUES (currval('sq_core_menus'), currval('sq_core_authorities'));