package ru.otus.questions.services.execution.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizResultProcessor;
import ru.otus.questions.services.execution.QuizExecutorService;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputFacade;

@Service
public class QuizExecutorServiceImpl implements QuizExecutorService {

    private final QuizBuilder quizBuilder;
    private final QuizRunner quizRunner;
    private final QuizResultProcessor quizResultProcessor;

    @Autowired
    public QuizExecutorServiceImpl(QuizBuilder quizBuilder,
                                   QuizRunner quizRunner,
                                   QuizResultProcessor quizResultProcessor) {
        this.quizBuilder = quizBuilder;
        this.quizRunner = quizRunner;
        this.quizResultProcessor = quizResultProcessor;
    }

    @Override
    public QuizResult runQuiz(String userName) {

        var quiz = quizBuilder.buildQuiz();
        var quizRawResult = quizRunner.runQuizAndCollectAnswers(quiz);

        return quizResultProcessor.calculateResults(quizRawResult.getUserAnswersMap(), userName);
    }

}
