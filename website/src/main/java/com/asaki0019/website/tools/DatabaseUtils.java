package com.asaki0019.website.tools;

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {
    private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());

    private static BasicDataSource dataSource;

    public DatabaseUtils() {
        if (dataSource != null) {
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "MySQL JDBC driver not found", e);
        }
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/coursewebsitedatabase");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(50);

        logger.info("Database connection pool initialized.");
    }

    public BasicDataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource has not been initialized.");
        }
        return dataSource;
    }
}
