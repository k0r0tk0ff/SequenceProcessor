package ru.k0r0tk0ff.sequence.processor.infrastructure.dao;

public class PostgresSequenceDaoException extends Exception {
    public PostgresSequenceDaoException() {
        super();
    }

    public PostgresSequenceDaoException(String message) {
        super(message);
    }

    public PostgresSequenceDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostgresSequenceDaoException(Throwable cause) {
        super(cause);
    }

    protected PostgresSequenceDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
