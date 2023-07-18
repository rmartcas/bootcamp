--liquibase formatted sql

--changeset core-config team:users_initial_data_test
--comment: Initial data for users module
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-1, 'Manager', 'E000000', 'E000000@core-config.com', 1);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-2, 'user1', 'E000001', 'E000001@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-3, 'user2', 'E000002', 'E000002@core-config.com', -3);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-4, 'user3', 'E000003', 'E000003@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-5, 'user4', 'E000004', 'E000004@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-6, 'user5', 'E000005', 'E000005@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-7, 'user6', 'E000006', 'E000006@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-8, 'user7', 'E000007', 'E000007@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-9, 'user8', 'E000008', 'E000008@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-10, 'user9', 'E000009', 'E000009@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-11, 'user10', 'E0000010', 'E0000010@core-config.com', -2);
INSERT INTO core_users(USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES (-12, 'user11', 'E0000011', 'E0000011@core-config.com', -2);

INSERT INTO core_users$AUD(REQUEST_ID, PAIR_KEY, AUDIT_ACTION, AUDIT_STEP, AUDIT_USER, AUDIT_DATE, USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('123123123', '1', 'UPDATE', 'BEFORE', 'E000000', now(), -2, 'admin1', 'E000001', 'E000001@core-config.com', 1);
INSERT INTO core_users$AUD(REQUEST_ID, PAIR_KEY, AUDIT_ACTION, AUDIT_STEP, AUDIT_USER, AUDIT_DATE, USER_ID, NAME, USERNAME, EMAIL, PROFILE_ID) VALUES ('123123123', '1', 'UPDATE', 'AFTER', 'E000000', now(), -2, 'user1', 'E000001', 'E000001@core-config.com', -2);