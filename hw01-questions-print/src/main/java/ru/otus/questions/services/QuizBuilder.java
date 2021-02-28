package ru.otus.questions.services;

import ru.otus.questions.domain.Quiz;

import java.util.List;

public interface QuizBuilder {

    Quiz buildQuiz(List<String[]> quizSource);
}
