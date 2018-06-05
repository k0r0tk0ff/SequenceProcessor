package ru.k0r0tk0ff.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Need for read data from DB
 */
public class DAOImpl implements DAO {

    private static final Logger LOG  = LoggerFactory.getLogger(DAOImpl.class);

    private Connection connection = null;

    public DAOImpl(Connection connection) {
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

    public void insertDataToDb(int n) {
        try {
            Statement statementForInsertData = connection.createStatement();
            ArrayList<String> queries = new ArrayList<>();

            for (int i = 1; i<n+1; i++) {
                queries.add(String.format("INSERT into TEST (field) VALUES ('%s')", i));
            }

            for (String query : queries) {
                statementForInsertData.addBatch(query);
            }
            statementForInsertData.executeBatch();
            statementForInsertData.close();

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Insert data success");
            }

        } catch (SQLException e) {
            LOG.error(".......................................................................");
            LOG.error("Insert data failed ..............");
            LOG.error(e.toString());
        }
    }
}


