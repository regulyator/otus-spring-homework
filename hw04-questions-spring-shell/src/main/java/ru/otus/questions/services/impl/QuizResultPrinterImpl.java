package ru.otus.questions.services.impl;

import org.springframework.stereotype.Component;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizResultPrinter;
import ru.otus.questions.services.util.InputOutputLocalizedService;

import java.util.Collection;

@Component
public class QuizResultPrinterImpl implements QuizResultPrinter {
    private final static String RESULTS_DELIMITER = "========================";
    private final InputOutputLocalizedService inputOutputLocalizedService;

    public QuizResultPrinterImpl(InputOutputLocalizedService inputOutputLocalizedService) {
        this.inputOutputLocalizedService = inputOutputLocalizedService;
    }

    @Override
    public void printQuizResult(QuizResult quizResult) {
        inputOutputLocalizedService.printMessageToUser("Result.Greeting", quizResult.getUserName());
        inputOutputLocalizedService.printMessageToUser("Result.Count",
                quizResult.getCorrectAnswersCount(),
                quizResult.getInCorrectAnswersCount());
        inputOutputLocalizedService.printMessageToUser(quizResult.isThresholdPassed() ? "Result.QuizPassed" : "Result.QuizFailed");
        inputOutputLocalizedService.printMessageToUser(RESULTS_DELIMITER);
    }

    @Override
    public void printQuizResults(Collection<QuizResult> quizResults) {
        quizResults.forEach(this::printQuizResult);
    }
}
