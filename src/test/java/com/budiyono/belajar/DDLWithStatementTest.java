package com.budiyono.belajar;

import com.budiyono.belajar.service.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class DDLWithStatementTest {
    private static Connection connection;

    @BeforeAll
    static void beforeAll() {
         connection = ConnectionUtils.getConnection();
    }

    @AfterAll
    static void afterAll() {
        if(connection != null){
            try {
                log.info("closing connection");
                connection.close();
            } catch (SQLException e) {
                Assertions.fail(e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Insert data to database")
    @Order(1)
    void insertData() {
        try {
            Statement statement = connection.createStatement();
            String sql = "insert into user (username, firstname, lastname)" +
                    "values ('budiyono', 'budi', 'yono')";

            int dataAffected = statement.executeUpdate(sql);
            Assertions.assertTrue(dataAffected > 0);
            statement.close();
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    @Order(2)
    void getData(){
        try {
            int id = 0;
            String username = "";
            String firstname = "";
            String lastname = "";
            String sql = "select * from user";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                id = resultSet.getInt("id");
                username = resultSet.getString("username");
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("lastname");
                break;
            }

            Assertions.assertTrue(id > 0);
            Assertions.assertEquals("budiyono", username);
            Assertions.assertEquals("budi", firstname);
            Assertions.assertEquals("yono", lastname);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void deleteData() {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from user";

            int affectedRow = statement.executeUpdate("DELETE FROM USER");
            Assertions.assertTrue(affectedRow > 0);

            statement.close();
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
