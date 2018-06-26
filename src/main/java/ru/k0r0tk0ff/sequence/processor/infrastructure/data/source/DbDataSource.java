package ru.k0r0tk0ff.sequence.processor.infrastructure.data.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbDataSource implements DataSource {
    private String url;
    private String login;
    private String password;

    public DbDataSource(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    private Connection connection = null;

    public Connection getConnection() throws DataSourceException {
        try {
            connection = DriverManager.getConnection(
                        url,
                        login,
                        password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
}
