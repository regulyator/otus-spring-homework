package ru.otus.questions.exception;

public class NoUserResultException extends QuizException {

    public NoUserResultException(String message) {
        super(message);
    }

    public NoUserResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
