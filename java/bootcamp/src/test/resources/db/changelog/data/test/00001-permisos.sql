--liquibase formatted sql

--changeset bootcamp team:2
--comment: Initial data for database population
 
-- Admin authorities
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('READ_LIBRARY', 'Rol para el controlador de biblio');
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((select profile_id from core_profiles where name = 'Administrador'), (select authority_id from core_authorities where name = 'READ_LIBRARY'));
 
-- Position 99 = Last position
INSERT INTO core_mappings(PATTERN) VALUES ('/biblioteca/find');
INSERT INTO core_mappings(PATTERN) VALUES ('/biblioteca/insert');
INSERT INTO core_mappings(PATTERN) VALUES ('/biblioteca/delete');
INSERT INTO core_mappings(PATTERN) VALUES ('/biblioteca/search');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/biblioteca/find'), (select authority_id from core_authorities where name = 'READ_LIBRARY')); --/biblioteca/find                READ_LIBRARY
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/biblioteca/insert'), (select authority_id from core_authorities where name = 'READ_LIBRARY')); --/biblioteca/insert                READ_LIBRARY
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/biblioteca/delete'), (select authority_id from core_authorities where name = 'READ_LIBRARY')); --/biblioteca/delete                READ_LIBRARY
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/biblioteca/search'), (select authority_id from core_authorities where name = 'READ_LIBRARY')); --/biblioteca/search                READ_LIBRARY

-- Add author
INSERT INTO autor(id_autor, nombre) VALUES (1, 'Stephen King');
 
-- Add book
INSERT INTO libro(id_libro, titulo, id_autor) VALUES (1, 'IT', 1);