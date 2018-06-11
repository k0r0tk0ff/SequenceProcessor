package ru.k0r0tk0ff.sequence.processor.infrastructure.dao.environment;

public class SequenceEnvironmentException extends Exception {
    public SequenceEnvironmentException() {
    }

    public SequenceEnvironmentException(String message) {
        super(message);
    }

    public SequenceEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SequenceEnvironmentException(Throwable cause) {
        super(cause);
    }

    protected SequenceEnvironmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
