package ru.otus.questions.services.impl;

import org.springframework.stereotype.Component;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizResultPrinter;
import ru.otus.questions.services.util.InputOutputFacade;

import java.util.Collection;

@Component
public class QuizResultPrinterImpl implements QuizResultPrinter {
    private final InputOutputFacade inputOutputFacade;

    public QuizResultPrinterImpl(InputOutputFacade inputOutputFacade) {
        this.inputOutputFacade = inputOutputFacade;
    }

    @Override
    public void printQuizResult(QuizResult quizResult) {
        inputOutputFacade.printMessageToUser("Result.Greeting", quizResult.getUserName());
        inputOutputFacade.printMessageToUser("Result.Count",
                quizResult.getCorrectAnswersCount(),
                quizResult.getInCorrectAnswersCount());
        inputOutputFacade.printMessageToUser(quizResult.isThresholdPassed() ? "Result.QuizPassed" : "Result.QuizFailed");
    }

    @Override
    public void printQuizResults(Collection<QuizResult> quizResults) {
        quizResults.forEach(this::printQuizResult);
    }
}
