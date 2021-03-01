package ru.otus.questions.services.impl;

import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizRunner;

import java.util.Objects;

public class QuizRunnerConsole implements QuizRunner {
    @Override
    public void printQuiz(Quiz quiz) {
        if (Objects.isNull(quiz)) {
            System.out.println("No Quiz - no fun:(");
        } else {
            quiz.getQuestions().forEach(question -> {
                System.out.println(question.getText());
                question.getAnswers().forEach(answer -> System.out.println(answer.getText()));
                System.out.println("====================");
            });
        }
    }
}
