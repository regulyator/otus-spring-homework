package ru.otus.questions.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizExecutorService;
import ru.otus.questions.services.QuizRunner;

@Service
public class QuizExecutorServiceImpl implements QuizExecutorService {

    private final QuizBuilder quizBuilder;
    private final QuizRunner quizRunner;

    public QuizExecutorServiceImpl(QuizBuilder quizBuilder, QuizRunner quizRunner) {
        this.quizBuilder = quizBuilder;
        this.quizRunner = quizRunner;
    }

    @Override
    public void runQuiz() {
        Quiz quiz = quizBuilder.buildQuiz();
        quizRunner.printQuiz(quiz);
    }

    @Override
    public Object getQuizResult() {
        throw new UnsupportedOperationException();
    }
}
