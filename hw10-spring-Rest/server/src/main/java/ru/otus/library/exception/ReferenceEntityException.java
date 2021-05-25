package ru.otus.library.exception;

public class ReferenceEntityException extends DaoException {

    public ReferenceEntityException() {
        super();
    }

    public ReferenceEntityException(String message) {
        super(message);
    }

    public ReferenceEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
