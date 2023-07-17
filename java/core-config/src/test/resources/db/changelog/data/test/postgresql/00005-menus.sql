--liquibase formatted sql

--changeset core-config team:menus_initial_data_test
--comment: Initial data for menus module
insert into core_menus(menu_id, title, link, icon, enabled, parent_menu_id) values (-1, 'menu.administration.to.delete', 'to_delete', '', true, 1);