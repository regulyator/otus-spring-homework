package ru.otus.questions.exception;

public class RunNullQuizException extends QuizException {

    public RunNullQuizException(String message) {
        super(message);
    }

    public RunNullQuizException(String message, Throwable cause) {
        super(message, cause);
    }
}
