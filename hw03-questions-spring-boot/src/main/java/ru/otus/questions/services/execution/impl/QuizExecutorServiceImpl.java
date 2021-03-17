package ru.otus.questions.services.execution.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizResultProcessor;
import ru.otus.questions.services.execution.QuizExecutorService;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputLocalizedService;

@Service
public class QuizExecutorServiceImpl implements QuizExecutorService {

    private final QuizBuilder quizBuilder;
    private final QuizRunner quizRunner;
    private final InputOutputLocalizedService inputOutputLocalizedService;
    private final QuizResultProcessor quizResultProcessor;

    @Autowired
    public QuizExecutorServiceImpl(QuizBuilder quizBuilder,
                                   QuizRunner quizRunner,
                                   InputOutputLocalizedService inputOutputLocalizedService,
                                   QuizResultProcessor quizResultProcessor) {
        this.quizBuilder = quizBuilder;
        this.quizRunner = quizRunner;
        this.inputOutputLocalizedService = inputOutputLocalizedService;
        this.quizResultProcessor = quizResultProcessor;
    }

    @Override
    public QuizResult runQuiz() {
        var userName = inputOutputLocalizedService.readInputFromUser("EnterName");

        var quiz = quizBuilder.buildQuiz();
        var quizRawResult = quizRunner.runQuizAndCollectAnswers(quiz);

        var calculatedResult = quizResultProcessor.calculateResults(quizRawResult.getUserAnswersMap());
        inputOutputLocalizedService.printMessageToUser("Result.Greeting", userName);
        inputOutputLocalizedService.printMessageToUser("Result.Count",
                calculatedResult.getCorrectAnswersCount(),
                calculatedResult.getInCorrectAnswersCount());
        inputOutputLocalizedService.printMessageToUser(calculatedResult.isThresholdPassed() ? "Result.QuizPassed" : "Result.QuizFailed");
        return calculatedResult;
    }

}
