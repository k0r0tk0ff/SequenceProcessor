package ru.k0r0tk0ff.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.k0r0tk0ff.configuration.SettingsFromFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceImpl implements DataSource{
    String url;
    String login;
    String password;

    public DataSourceImpl(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    private static final Logger LOG  = LoggerFactory.getLogger(SettingsFromFile.class);

    Connection connection = null;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(
                    url,
                    login,
                    password);
        } catch (SQLException e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }

        if(LOG.isDebugEnabled()) {
            LOG.debug(" Get connection success  ..............");
            LOG.debug(".......................................................................");
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();

                if(LOG.isDebugEnabled()) {
                    LOG.debug(" Close connection success  ..............");
                    LOG.debug(".......................................................................");
                }
            }
        }
        catch (SQLException e) {
            LOG.error(e.toString());
            LOG.error(".......................................................................");
        }
    }
}
