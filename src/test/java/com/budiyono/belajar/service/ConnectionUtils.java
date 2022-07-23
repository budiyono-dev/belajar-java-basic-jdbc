package com.budiyono.belajar.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {
    private static Connection connection;

    static {
        String url = "jdbc:mysql://localhost:3306/testing";
        String username = "user";
        String password = "user";
        String driverName = "com.mysql.cj.jdbc.Driver";

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60 * 1000);
        config.setMaxLifetime(10 * 60 * 1000);

        HikariDataSource dataSource = new HikariDataSource(config);

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
