package com.budiyono.belajar;

import com.budiyono.belajar.service.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DDLPreparedStatementTest {
    private static Connection connection;

    @BeforeAll
    static void beforeAll() {
        connection = ConnectionUtils.getConnection();
    }

    @AfterAll
    static void afterAll() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Assertions.fail(e.getMessage());
            }
        }
    }

    @Test
    @Order(1)
    void insertData() throws SQLException {
        String sql = "insert into user (username, firstname, lastname)" +
                "values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "budiyono");
        preparedStatement.setString(2, "budi");
        preparedStatement.setString(3, "yono");
        int row = preparedStatement.executeUpdate();
        Assertions.assertTrue(row < 0);
        preparedStatement.close();
    }

    @Test
    void insertBatch() throws SQLException {
        String sql = "insert into user (username, firstname, lastname)" +
                "values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        List<String> listNumber = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            listNumber.add(String.valueOf(i));
        }

        for (String number : listNumber) {
            preparedStatement.clearParameters();
            preparedStatement.setString(1, "budiyono");
            preparedStatement.setString(2, "budi");
            preparedStatement.setString(3, number);
            preparedStatement.addBatch();
        }

        int[] ints = preparedStatement.executeBatch();
        int count = 0;
        for (int row : ints) {
            count += row;
        }
        Assertions.assertEquals(500, count);
        preparedStatement.close();
    }

    @Test
    void getGeneretedKey() throws SQLException {
        String sql = "insert into user (username, firstname, lastname)" +
                "values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, "budiyono");
        preparedStatement.setString(2, "budi");
        preparedStatement.setString(3, "yono");
        int row = preparedStatement.executeUpdate();
        Assertions.assertEquals(1, row);
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        Integer key = null;
        if (resultSet.next()) {
            key = resultSet.getInt(1);
        }
        log.info("GENERATED KEY = {0}", key);
        Assertions.assertNotNull(key);
        Assertions.assertTrue(key > 0);
        preparedStatement.close();
    }
}
