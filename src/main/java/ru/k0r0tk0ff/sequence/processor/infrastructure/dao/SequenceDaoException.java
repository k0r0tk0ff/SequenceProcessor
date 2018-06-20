package ru.k0r0tk0ff.sequence.processor.infrastructure.dao;

public class SequenceDaoException extends Exception {
    public SequenceDaoException() {
        super();
    }

    public SequenceDaoException(String message) {
        super(message);
    }

    public SequenceDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SequenceDaoException(Throwable cause) {
        super(cause);
    }

    protected SequenceDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
