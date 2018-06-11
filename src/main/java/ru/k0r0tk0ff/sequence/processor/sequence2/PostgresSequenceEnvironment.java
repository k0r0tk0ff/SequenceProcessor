package ru.k0r0tk0ff.sequence.processor.sequence2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Create table (if not exist), truncate table
 */
public class PostgresSequenceEnvironment implements SequenceEnvironment {

    private Connection connection = null;

    public PostgresSequenceEnvironment(Connection connection) {
        this.connection = connection;
    }

    private static final Logger LOG  = LoggerFactory.getLogger(PostgresSequenceEnvironment.class);

    final private String createTable = "CREATE TABLE IF NOT EXISTS TEST (field INTEGER);";
    final private String truncateTable = "TRUNCATE TABLE public.TEST;";

    public void clearSequence() throws Exception {
            Statement statementForDrop = connection.createStatement();
            statementForDrop.execute(truncateTable);

            LOG.debug(" Truncate table success ");

            statementForDrop.close();
    }

    public void createSequenceInStorage() throws Exception {
            Statement statementCreateTable = connection.createStatement();
            statementCreateTable.execute(createTable);

            LOG.debug(" Create table (if table exist - do not create) success ");

            statementCreateTable.close();
    }
}
