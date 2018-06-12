package ru.k0r0tk0ff.sequence.processor.infrastructure.db;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection() throws DbDataSourceException;
}
