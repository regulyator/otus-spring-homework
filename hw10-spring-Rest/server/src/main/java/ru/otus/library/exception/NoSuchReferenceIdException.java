package ru.otus.library.exception;

public class NoSuchReferenceIdException extends DaoException {

    public NoSuchReferenceIdException(String message) {
        super(message);
    }

    public NoSuchReferenceIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
