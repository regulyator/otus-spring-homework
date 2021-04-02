package ru.otus.library.exception;

public class DaoUpdateEmptyIdException extends DaoException {

    public DaoUpdateEmptyIdException(String message) {
        super(message);
    }

    public DaoUpdateEmptyIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
