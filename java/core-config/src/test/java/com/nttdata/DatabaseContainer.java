/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Utility class to create a shared instance of database for test purposes 
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public class DatabaseContainer extends PostgreSQLContainer<DatabaseContainer> {

	private static final String IMAGE_VERSION = "postgres:12.10-alpine";
    private static DatabaseContainer container;

    private DatabaseContainer() {
    	super(IMAGE_VERSION);
    }

    public static DatabaseContainer getInstance() {
        if (container == null) {
            container = new DatabaseContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DATASOURCE_URL", container.getJdbcUrl());
        System.setProperty("DATASOURCE_USR", container.getUsername());
        System.setProperty("DATASOURCE_PWD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}