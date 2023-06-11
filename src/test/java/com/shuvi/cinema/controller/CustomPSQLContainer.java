package com.shuvi.cinema.controller;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPSQLContainer extends PostgreSQLContainer<CustomPSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:latest";
    private static CustomPSQLContainer container;

    private CustomPSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static CustomPSQLContainer getInstance() {
        if (container == null) {
            container = new CustomPSQLContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
