package ru.k0r0tk0ff.sequence.processor.db;

import java.sql.Connection;

public interface ConnectionDao {
    Connection getConnection() throws Exception;
}
