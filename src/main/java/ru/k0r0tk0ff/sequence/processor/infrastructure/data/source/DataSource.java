package ru.k0r0tk0ff.sequence.processor.infrastructure.data.source;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection() throws DataSourceException;
}
