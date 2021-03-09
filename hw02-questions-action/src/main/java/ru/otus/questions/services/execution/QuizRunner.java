package ru.otus.questions.services.execution;

import ru.otus.questions.domain.Quiz;
import ru.otus.questions.domain.QuizUserRawResult;

public interface QuizRunner {

    QuizUserRawResult runQuizAndCollectAnswers(Quiz quiz);
}
