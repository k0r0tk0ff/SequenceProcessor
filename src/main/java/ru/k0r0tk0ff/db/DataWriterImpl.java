package ru.k0r0tk0ff.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Write data to database.
 */
public class DataWriterImpl implements DataWriter {
    private Connection connection = null;

    public DataWriterImpl(Connection connection) {
        this.connection = connection;
    }

    private static final Logger LOG  = LoggerFactory.getLogger(DataWriterImpl.class);

    final private String createTable = "CREATE TABLE IF NOT EXISTS TEST (field INTEGER);";
    final private String dropTable = "TRUNCATE TABLE public.TEST;";

    public void truncateTable() {
        try {
            Statement statementForDrop = connection.createStatement();
            statementForDrop.execute(dropTable);

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Truncate table success ");
            }
            statementForDrop.close();
        } catch (SQLException e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }
    }

    public void createTableInDb() {
        try {
            Statement statementCreateTable = connection.createStatement();
            statementCreateTable.execute(createTable);

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Create table success ");
            }
            statementCreateTable.close();
        } catch (SQLException e) {
            LOG.error(".......................................................................");
            LOG.error("Create table failed  ..............");
            LOG.error(e.toString());
        }
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
