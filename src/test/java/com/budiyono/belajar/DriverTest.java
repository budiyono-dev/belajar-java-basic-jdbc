package com.budiyono.belajar;

import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverTest {
    @Test
    @DisplayName("Driver Connection Testing")
    void driverTest() {
        try {
            Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
