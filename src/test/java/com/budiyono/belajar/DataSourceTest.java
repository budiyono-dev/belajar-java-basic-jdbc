package com.budiyono.belajar;

import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Slf4j
public class DataSourceTest {

    private static final String url = "jdbc:mysql://localhost:3306/testing";
    private static final String username = "user";
    private static final String password = "user";
    private static final String driverName = "com.mysql.cj.jdbc.Driver";

    @BeforeAll
    static void beforeAll() {
        try {
            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Connection Database Test")
    void connectionTest() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            log.info("connection success");
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("HikariCP Test")
    void hikariCPTest() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60*1000);
        config.setMaxLifetime(10*60*1000);

        HikariDataSource dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection()) {
            log.info("connection success");
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
