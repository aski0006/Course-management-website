package com.asaki0019.website.tools;

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {
    private static final Logger logger = Logger.getLogger(DatabaseUtils.class.getName());

    private static final BasicDataSource dataSource;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "MySQL JDBC driver not found", e);
        }
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/your_database");
        dataSource.setUsername("your_username");
        dataSource.setPassword("your_password");
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(50);
    }

    // 获取数据源
    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}
