package ru.otus.questions.services;

import ru.otus.questions.domain.Quiz;
import ru.otus.questions.exception.QuizBuildException;

public interface QuizBuilder {

    Quiz buildQuiz();
}
