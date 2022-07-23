package com.budiyono.belajar;

import com.budiyono.belajar.service.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DBTransactionTest {
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
    void transactionRollbackTest() throws SQLException {
        String sql = "insert into user (username, firstname, lastname)" +
                "values (?, ?, ?)";

        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            for (int i = 1; i <= 50; i++) {
                preparedStatement.clearParameters();
                preparedStatement.setString(1, "budiyono");
                preparedStatement.setString(2, "budi");
                preparedStatement.setString(3, String.valueOf(i));
                preparedStatement.addBatch();
                if (i == 25) {
                    throw new SQLException("stop");
                }

            }
            connection.commit();
        } catch (SQLException e) {
            log.warn(e.getMessage());
            connection.rollback();
        }


        int[] ints = preparedStatement.executeBatch();
        int count = 0;
        for (int row : ints) {
            count += row;
        }

        Assertions.assertEquals(25, count);
        preparedStatement.close();
    }
}

