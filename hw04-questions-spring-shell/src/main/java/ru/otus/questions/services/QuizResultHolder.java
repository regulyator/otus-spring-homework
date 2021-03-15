package ru.otus.questions.services;

import ru.otus.questions.domain.QuizResult;

import java.util.Collection;

public interface QuizResultHolder {

    void putQuizResult(QuizResult quizResult);

    Collection<QuizResult> getUserResult(String userName);

    Collection<QuizResult> getAllResult();
}
