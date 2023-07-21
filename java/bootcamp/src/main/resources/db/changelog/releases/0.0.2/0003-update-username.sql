--liquibase formatted sql

--changeset bootcamp team:update-username
--comment: Add new user


alter table core_users alter column USERNAME type character varying(220);