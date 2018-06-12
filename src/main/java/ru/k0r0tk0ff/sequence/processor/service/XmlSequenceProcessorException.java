package ru.k0r0tk0ff.sequence.processor.service;

public class XmlSequenceProcessorException extends Exception {
    public XmlSequenceProcessorException() {
    }

    public XmlSequenceProcessorException(String message) {
        super(message);
    }

    public XmlSequenceProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlSequenceProcessorException(Throwable cause) {
        super(cause);
    }

    public XmlSequenceProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
