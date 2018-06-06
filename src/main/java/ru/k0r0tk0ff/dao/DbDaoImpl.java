package ru.k0r0tk0ff.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Need for read data from DB
 */
public class DbDaoImpl implements Dao {

    private static final Logger LOG  = LoggerFactory.getLogger(DbDaoImpl.class);

    private Connection connection = null;

    public DbDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Collection<String> getData() throws Exception {
        ResultSet resultSet;
        String sqlQuery = "SELECT field FROM PUBLIC.TEST";

        resultSet = queryExecutor(sqlQuery);

        return dataConverter(resultSet);

    }

    private ResultSet queryExecutor(String sqlQuery) throws Exception{
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sqlQuery);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    private Collection<String> dataConverter(ResultSet resultSet) throws Exception {
        Collection<String> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public void insertData(int n) {
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


