package ru.otus.questions.exception;

public class IOServiceException extends RuntimeException {

    public IOServiceException(String message) {
        super(message);
    }

    public IOServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
