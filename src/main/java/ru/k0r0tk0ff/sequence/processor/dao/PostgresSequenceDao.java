package ru.k0r0tk0ff.sequence.processor.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.sequence2.SequenceEnvironment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Need for read data from DB
 */
public class PostgresSequenceDao implements SequenceDao {

    private SequenceEnvironment sequenceEnvironment;

    private Connection connection = null;

    public PostgresSequenceDao(Connection connection, SequenceEnvironment sequenceEnvironment) {
        this.connection = connection;
        this.sequenceEnvironment = sequenceEnvironment;
    }

    private static final Logger LOG  = LoggerFactory.getLogger(PostgresSequenceDao.class);

    public Collection<String> get() throws Exception {
        ResultSet resultSet;
        final String sqlQuery = "SELECT field FROM PUBLIC.TEST";

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

        resultSet.close();
        return list;
    }

    public void put(int seqElementsCountAndMaxValue) throws Exception {

        sequenceEnvironment.clearSequence();
        sequenceEnvironment.createSequenceInStorage();

        try(
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                "INSERT into TEST (field) VALUES (?)")) {
            for (int i = 1; i < seqElementsCountAndMaxValue + 1; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.addBatch();
                if (i % 5000 == 0) {
                    preparedStatement.executeBatch();
                }
            }

            preparedStatement.executeBatch();
            LOG.debug(" Insert data success");
        }
    }
}

