package ru.otus.questions.services;

import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.QuizResult;

import java.util.List;
import java.util.Map;

public interface QuizResultProcessor {

    QuizResult calculateResults(Map<Question, List<Answer>> rawQuizResult, String userName);
}
