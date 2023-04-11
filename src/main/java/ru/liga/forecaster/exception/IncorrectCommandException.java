package ru.liga.forecaster.exception;

public class IncorrectCommandException extends Exception {
    public IncorrectCommandException() {
    }

    public IncorrectCommandException(String message) {
        super(message);
    }

    public IncorrectCommandException(String message , Throwable cause) {
        super(message , cause);
    }

    public IncorrectCommandException(Throwable cause) {
        super(cause);
    }

    public IncorrectCommandException(String message , Throwable cause , boolean enableSuppression , boolean writableStackTrace) {
        super(message , cause , enableSuppression , writableStackTrace);
    }
}
