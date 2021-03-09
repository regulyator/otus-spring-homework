package ru.otus.questions.exception;

public class QuizRawReadException extends QuizException {

    public QuizRawReadException(String message) {
        super(message);
    }

    public QuizRawReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
