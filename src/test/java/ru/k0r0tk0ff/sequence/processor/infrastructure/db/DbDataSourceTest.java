package ru.k0r0tk0ff.sequence.processor.infrastructure.db;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DbDataSourceTest {
    @Test
    public void ConnectionTest() throws SQLException {
    Connection connection = null;
    connection = DriverManager.getConnection(
            "jdbc:postgresql://127.0.0.1:5432/postgres",
            "postgres",
            "zxcvbnm");

    assertThat(connection!=null, is(true));

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}