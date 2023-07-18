--liquibase formatted sql

--changeset core-config team:mappings_initial_data_test
--comment: Initial data for mappings module
-- Position 99 = Last position
INSERT INTO core_mappings(MAPPING_ID, PATTERN) VALUES (-1, '/api/test/**');
INSERT INTO core_mappings(MAPPING_ID, PATTERN) VALUES (-2, '/api/test/m3');
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern = '/api/test/**'), (select authority_id from core_authorities where name = 'CORE'));
INSERT INTO core_mapping_authorities(MAPPING_ID, AUTHORITY_ID) VALUES ((select mapping_id from core_mappings where pattern = '/api/test/m3'), (select authority_id from core_authorities where name = 'CORE'));