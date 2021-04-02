package ru.otus.library.exception;

public class DaoInsertNonEmptyIdException extends DaoException {

    public DaoInsertNonEmptyIdException(String message) {
        super(message);
    }

    public DaoInsertNonEmptyIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
