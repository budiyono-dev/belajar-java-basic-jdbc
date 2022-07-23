package com.budiyono.belajar;

import com.budiyono.belajar.service.ConnectionUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBMetaData {

    @Test
    void getMetaDataTest() throws SQLException {
        Connection connection = ConnectionUtils.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables("testing", null, null, null);
        while (tables.next()) {
            System.out.println("Tabel name = " + tables.getString("TABLE_NAME"));
        }

        ResultSet userSet = metaData.getColumns(null, null, "user", null);
        while (userSet.next()) {
            System.out.println("Tabel TABLE_NAME = " + userSet.getString("TABLE_NAME"));
            System.out.println("Tabel COLUMN_NAME = " + userSet.getString("COLUMN_NAME"));
            System.out.println("Tabel DATA_TYPE = " + userSet.getString("DATA_TYPE"));
            System.out.println("Tabel TYPE_NAME = " + userSet.getString("TYPE_NAME"));
            System.out.println("Tabel COLUMN_SIZE = " + userSet.getString("COLUMN_SIZE"));

        }
    }
}

