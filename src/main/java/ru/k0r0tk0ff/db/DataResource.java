package ru.k0r0tk0ff.db;

import java.sql.Connection;

public interface DataResource {

    Connection getConnection();

    void closeConnection(Connection connection);
}
