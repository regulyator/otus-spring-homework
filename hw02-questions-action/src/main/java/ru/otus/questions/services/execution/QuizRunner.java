package ru.otus.questions.services.execution;

import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;

import java.util.List;
import java.util.Map;

public interface QuizRunner {

    Map<Question, List<Answer>> runQuizAndCollectAnswers(Quiz quiz);
}
