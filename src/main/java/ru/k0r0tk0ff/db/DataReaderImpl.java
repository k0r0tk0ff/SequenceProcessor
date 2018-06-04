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

    private static final Logger LOG  = LoggerFactory.getLogger(DataReaderImpl.class);

    public ResultSet getDataFromDb() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        String sqlQuery = "SELECT field FROM PUBLIC.TEST";
            try {
                preparedStatement = connection.prepareStatement(sqlQuery);
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                LOG.error(e.toString());
                LOG.error(".......................................................................");
            }

        if(LOG.isDebugEnabled()) {
            LOG.debug(" Select success ..............");
            LOG.debug(".......................................................................");
        }

        return resultSet;
    }
}
