package ru.k0r0tk0ff.sequence.processor.infrastructure.db;

public class DbDataSourceException extends Exception {
    public DbDataSourceException() {
    }

    public DbDataSourceException(String message) {
        super(message);
    }

    public DbDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbDataSourceException(Throwable cause) {
        super(cause);
    }

    public DbDataSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
