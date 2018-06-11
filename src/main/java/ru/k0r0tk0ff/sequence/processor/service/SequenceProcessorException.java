package ru.k0r0tk0ff.sequence.processor.service;

public class SequenceProcessorException extends Exception {
    public SequenceProcessorException() {
    }

    public SequenceProcessorException(String message) {
        super(message);
    }

    public SequenceProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SequenceProcessorException(Throwable cause) {
        super(cause);
    }

    protected SequenceProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
