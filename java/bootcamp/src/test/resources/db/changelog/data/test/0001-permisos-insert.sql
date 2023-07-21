--liquibase formatted sql

 

--changeset bootcamp team:permisos-insert
--comment: Initial data for database population

 


-- Admin authorities
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('READ_AUTHOR', 'Rol para el controlador de biblio');
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((select profile_id from core_profiles where name = 'Administrador'), (select authority_id from core_authorities where name = 'READ_AUTHOR'));
INSERT INTO core_authorities(NAME, DESCRIPTION) VALUES ('READ_BOOK', 'Rol para el controlador de biblio');
INSERT INTO core_profile_authorities(PROFILE_ID, AUTHORITY_ID) VALUES ((select profile_id from core_profiles where name = 'Administrador'), (select authority_id from core_authorities where name = 'READ_BOOK'));
 

-- Position 99 = Last position
INSERT INTO core_mappings(PATTERN) VALUES ('/autor/insert');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/autor/insert'), (select authority_id from core_authorities where name = 'READ_AUTHOR')); --/autor/insert                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/libro/insert');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/libro/insert'), (select authority_id from core_authorities where name = 'READ_BOOK')); --/libro/insert                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/autor/find');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/autor/find'), (select authority_id from core_authorities where name = 'READ_AUTHOR')); --/autor/find                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/libro/find');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/libro/find'), (select authority_id from core_authorities where name = 'READ_BOOK')); --/libro/find                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/autor/update');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/autor/update'), (select authority_id from core_authorities where name = 'READ_AUTHOR')); --/autor/find                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/libro/update');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/libro/update'), (select authority_id from core_authorities where name = 'READ_BOOK')); --/libro/find                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/autor/delete');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/autor/delete'), (select authority_id from core_authorities where name = 'READ_AUTHOR')); --/autor/find                READ_LIBRARY

INSERT INTO core_mappings(PATTERN) VALUES ('/libro/delete');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern='/libro/delete'), (select authority_id from core_authorities where name = 'READ_BOOK')); --/libro/find                READ_LIBRARY

-- Add author
INSERT INTO AUTORES_GOOD(AUTOR_ID, NAME) VALUES (11, 'Stephen King');

 

-- Add book
INSERT INTO LIBROS_GOOD(LIBRO_ID, TITULO, AUTOR_ID) VALUES (22, 'IT', 11);