package ru.k0r0tk0ff.sequence.processor.infrastructure.writer;

public class WriterException extends Exception {
    public WriterException() {
        super();
    }

    public WriterException(String message) {
        super(message);
    }

    public WriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterException(Throwable cause) {
        super(cause);
    }

    protected WriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
