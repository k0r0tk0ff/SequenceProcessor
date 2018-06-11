package ru.k0r0tk0ff.sequence.processor.infrastructure.dao;

import org.junit.Test;

import java.sql.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PostgresSequenceDaoTest {

    private Connection getConnection() throws Exception {
        Connection connection = null;


        connection = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres",
                "postgres",
                "zxcvbnm");
        return connection;
    }

    @Test
    public void putAndGetDataToPostgresDB() throws Exception {
        Connection connection;
        connection = getConnection();
        ResultSet resultSet;
        PreparedStatement preparedStatement;

        final String createTableQuery = "CREATE TABLE IF NOT EXISTS TEST (field INTEGER);";
        final String putQuery = "INSERT into TEST (field) VALUES ('1')";

        Statement statementForInsertData = connection.createStatement();
        statementForInsertData.execute(createTableQuery);
        statementForInsertData.execute(putQuery);
        statementForInsertData.close();

        String sqlQuery = "SELECT field FROM PUBLIC.TEST";

        preparedStatement = connection.prepareStatement(sqlQuery);
        resultSet = preparedStatement.executeQuery();

        String resultFromResultSet = "null";
        if (resultSet.next()) {
            resultFromResultSet = resultSet.getString("field");
        }

        assertThat(resultFromResultSet.equals("1"), is(true));

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}
