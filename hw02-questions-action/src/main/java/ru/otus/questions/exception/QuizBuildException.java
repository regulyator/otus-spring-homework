package ru.otus.questions.exception;

public class QuizBuildException extends RuntimeException {

    public QuizBuildException(String message) {
        super(message);
    }

    public QuizBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
