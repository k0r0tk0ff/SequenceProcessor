package ru.k0r0tk0ff.sequence.processor.infrastructure.configuration;

public class PropertiesFileLoadException extends Exception {
    public PropertiesFileLoadException() {
    }

    public PropertiesFileLoadException(String message) {
        super(message);
    }

    public PropertiesFileLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesFileLoadException(Throwable cause) {
        super(cause);
    }

    public PropertiesFileLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
