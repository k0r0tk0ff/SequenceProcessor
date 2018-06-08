package ru.k0r0tk0ff.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDaoImpl implements ConnectionDao {
    private String url;
    private String login;
    private String password;

    public ConnectionDaoImpl(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    private Connection connection = null;

    public Connection getConnection() throws Exception{
            connection = DriverManager.getConnection(
                    url,
                    login,
                    password);
        return connection;
    }
}
