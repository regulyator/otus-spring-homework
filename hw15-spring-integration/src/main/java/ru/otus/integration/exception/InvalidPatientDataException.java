package ru.otus.integration.exception;

public class InvalidPatientDataException extends RuntimeException {

    public InvalidPatientDataException() {
        super();
    }

    public InvalidPatientDataException(String message) {
        super(message);
    }

    public InvalidPatientDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
