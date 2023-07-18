--liquibase formatted sql

--changeset core-config team:authorities_initial_data_test
--comment: Initial data for authorities module
INSERT INTO core_authorities(AUTHORITY_ID, NAME, DESCRIPTION) VALUES (-1, 'NOT_USED_AUTHORITY', 'Rol sin uso para test');