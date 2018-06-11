package ru.k0r0tk0ff.sequence.processor.domain;

public class SeqenceException extends Exception {
    public SeqenceException() {
    }

    public SeqenceException(String message) {
        super(message);
    }

    public SeqenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeqenceException(Throwable cause) {
        super(cause);
    }

    protected SeqenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
