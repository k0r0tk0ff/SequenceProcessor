package ru.k0r0tk0ff.sequence.processor.infrastructure.data.source;

public class DataSourceException extends Exception {
    public DataSourceException(Throwable cause) {
        super(cause);
    }

    public DataSourceException() {
        super();
    }

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected DataSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
