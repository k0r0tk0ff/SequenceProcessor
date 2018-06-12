package ru.k0r0tk0ff.sequence.processor.utils.console;

public class ConsoleInputException extends Exception {
    public ConsoleInputException() {
    }
    public ConsoleInputException(String message) {
        super(message);
    }

    public ConsoleInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsoleInputException(Throwable cause) {
        super(cause);
    }

    protected ConsoleInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
