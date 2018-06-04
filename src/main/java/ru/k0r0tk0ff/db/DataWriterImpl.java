package ru.k0r0tk0ff.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by korotkov_a_a on 04.06.2018.
 */
public class DataWriterImpl implements DataWriter {
    Connection connection = null;

    public DataWriterImpl(Connection connection) {
        this.connection = connection;
    }

    private static final Logger LOG  = LoggerFactory.getLogger(DataWriterImpl.class);

    final String createTable = "create TABLE TEST (field INTEGER);";
    final String dropTable = "DROP TABLE TEST;";

    Statement statementForDrop;
    Statement statementCreateTable;
    Statement statementForInsertData;

    public void dropDataInDb() {
        try {
            statementForDrop = connection.createStatement();
            statementForDrop.execute(dropTable);

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Drop data success !!!  ..............");
                LOG.debug(".......................................................................");
            }
            statementForDrop.close();
        } catch (SQLException e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }
    }

    public void createTableInDb() {
        try {
            statementCreateTable = connection.createStatement();
            statementCreateTable.execute(createTable);

            if(LOG.isDebugEnabled()) {
                LOG.debug(" Create table success !!!  ..............");
                LOG.debug(".......................................................................");
            }
            statementCreateTable.close();
        } catch (SQLException e) {
            LOG.error(".......................................................................");
            LOG.error("Create table failed !!! ..............");
            LOG.error(e.toString());
        }
    }


    public void insertDataToDb(int n) {
        try {
            statementForInsertData = connection.createStatement();
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
                LOG.debug(" Insert data success!!  ..............");
                LOG.debug(".......................................................................");
            }

        } catch (SQLException e) {
            LOG.error(".......................................................................");
            LOG.error("Insert data failed !!! ..............");
            LOG.error(e.toString());
        }
    }
}
