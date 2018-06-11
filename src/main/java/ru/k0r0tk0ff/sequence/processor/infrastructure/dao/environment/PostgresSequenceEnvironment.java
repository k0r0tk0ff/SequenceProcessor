package ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresSequenceEnvironment implements SequenceEnvironment {
    private static final Logger LOG = LoggerFactory.getLogger(PostgresSequenceEnvironment.class);
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS TEST (field INTEGER);";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE public.TEST;";

    private Connection connection = null;

    public PostgresSequenceEnvironment(Connection connection) {
        this.connection = connection;
    }

    public void createSequenceStorage() throws SequenceEnvironmentException {
        try {
            executeStatement(CREATE_TABLE);
            LOG.debug(" Create table (if table exist - do not create) success ");
        } catch (Exception e) {
            throw new SequenceEnvironmentException(e);
        }
    }

    public void clearSequenceStorage() throws SequenceEnvironmentException {
        try {
            executeStatement(TRUNCATE_TABLE);
            LOG.debug(" Truncate table success ");
        } catch (Exception e) {
            throw new SequenceEnvironmentException(e);
        }

    }

    private void executeStatement(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }
}
