package ru.otus.questions.services;

import ru.otus.questions.domain.QuizResult;

import java.util.Collection;

public interface QuizResultPrinter {

    void printQuizResult(QuizResult quizResult);

    void printQuizResults(Collection<QuizResult> quizResults);
}
