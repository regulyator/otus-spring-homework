package ru.otus.questions.services.impl;

import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizRunner;
import ru.otus.questions.services.util.InputOutputService;

import java.util.Objects;

public class QuizRunnerImpl implements QuizRunner {

    private final InputOutputService inputOutputServiceConsole;

    public QuizRunnerImpl(InputOutputService inputOutputServiceConsole) {
        this.inputOutputServiceConsole = inputOutputServiceConsole;
    }

    @Override
    public void printQuiz(Quiz quiz) {
        if (Objects.isNull(quiz)) {
            inputOutputServiceConsole.writeOutput("No Quiz - no fun:(");
        } else {
            quiz.getQuestions().forEach(question -> {
                inputOutputServiceConsole.writeOutput(question.getText());
                question.getAnswers().forEach(answer -> inputOutputServiceConsole.writeOutput(answer.getText()));
                inputOutputServiceConsole.writeOutput("====================");
            });
        }
    }
}
