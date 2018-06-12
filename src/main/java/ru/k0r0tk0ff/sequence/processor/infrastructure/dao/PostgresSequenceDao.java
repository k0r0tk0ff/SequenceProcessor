package ru.k0r0tk0ff.sequence.processor.infrastructure.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.sequence.processor.domain.Sequence;
import ru.k0r0tk0ff.sequence.processor.domain.SequenceImpl;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.SequenceEnvironment;
import ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment.SequenceEnvironmentException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PostgresSequenceDao implements SequenceDao {

    private static final Logger LOG  = LoggerFactory.getLogger(PostgresSequenceDao.class);
    private SequenceEnvironment sequenceEnvironment;
    private Connection connection = null;

    public PostgresSequenceDao(Connection connection, SequenceEnvironment sequenceEnvironment) {
        this.connection = connection;
        this.sequenceEnvironment = sequenceEnvironment;
    }

    public Sequence get() throws PostgresSequenceDaoException {
        final String sqlQueryForGet = "SELECT field FROM PUBLIC.TEST";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryForGet)) {
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                Collection<Integer> collection = new ArrayList<>();
                while (resultSet.next()) {
                    collection.add(resultSet.getInt(1));
                }
                return new SequenceImpl(collection);
            }
        } catch (SQLException e) {
            throw new PostgresSequenceDaoException();
        }
    }

    public void put(int seqElementsCountAndMaxValue) throws PostgresSequenceDaoException {
        final String queryForPutData = "INSERT into TEST (field) VALUES (?)";
        try {
            sequenceEnvironment.clearSequenceStorage();
            sequenceEnvironment.createSequenceStorage();
            try (
                    PreparedStatement preparedStatement =
                            connection.prepareStatement(queryForPutData)) {
                for (int i = 1; i < seqElementsCountAndMaxValue + 1; i++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.addBatch();
                    if (i % 5000 == 0) {
                        preparedStatement.executeBatch();
                    }
                }
                preparedStatement.executeBatch();
                LOG.debug("Insert data success");
            } catch (SQLException e2) {
                throw new PostgresSequenceDaoException();
            }
        } catch (SequenceEnvironmentException e) {
            throw new PostgresSequenceDaoException();
        }
    }
}


