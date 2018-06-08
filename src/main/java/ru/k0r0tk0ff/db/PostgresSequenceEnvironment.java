package ru.k0r0tk0ff.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Create teble (if not exist), truncate table
 */
public class PostgresSequenceEnvironment implements SequenceEnvironment {

    private Connection connection = null;

    public PostgresSequenceEnvironment(Connection connection) {
        this.connection = connection;
    }

    private static final Logger LOG  = LoggerFactory.getLogger(PostgresSequenceEnvironment.class);

    final private String createTable = "CREATE TABLE IF NOT EXISTS TEST (field INTEGER);";
    final private String truncateTable = "TRUNCATE TABLE public.TEST;";

    public void clearSequence() {
        try {
            Statement statementForDrop = connection.createStatement();
            statementForDrop.execute(truncateTable);

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Truncate table success ");
            }
            statementForDrop.close();
        } catch (SQLException e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }
    }

    public void createSequenceInStorage() {
        try {
            Statement statementCreateTable = connection.createStatement();
            statementCreateTable.execute(createTable);

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Create table (if table exist - do not create) success ");
            }
            statementCreateTable.close();
        } catch (SQLException e) {
            LOG.error(".......................................................................");
            LOG.error("Create table failed!");
            LOG.error(e.toString());
        }
    }
}