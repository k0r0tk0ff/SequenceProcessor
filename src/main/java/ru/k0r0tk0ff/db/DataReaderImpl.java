package ru.k0r0tk0ff.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

/**
 * Need for read data from DB
 */
public class DataReaderImpl implements DataReader{
    private Connection connection = null;

    public DataReaderImpl(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getData() throws Exception {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String sqlQuery = "SELECT field FROM PUBLIC.TEST";

        preparedStatement = connection.prepareStatement(sqlQuery);
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }
}
