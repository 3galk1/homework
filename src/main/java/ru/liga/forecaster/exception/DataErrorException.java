package ru.liga.forecaster.exception;

public class DataErrorException extends Exception {
    public DataErrorException() {
    }

    public DataErrorException(String message) {
        super(message);
    }

    public DataErrorException(String message , Throwable cause) {
        super(message , cause);
    }

    public DataErrorException(Throwable cause) {
        super(cause);
    }

    public DataErrorException(String message , Throwable cause , boolean enableSuppression , boolean writableStackTrace) {
        super(message , cause , enableSuppression , writableStackTrace);
    }
}
