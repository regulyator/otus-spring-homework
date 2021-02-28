package ru.otus.questions.domain;


import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class Question {

    private final String text;
    private final List<Answer> answers;
    private final boolean multipleCorrectAnswers;

    public Question(@NonNull String text, @NonNull List<Answer> answers) {
        this.text = text;
        this.answers = answers;
        this.multipleCorrectAnswers = answers
                .stream()
                .map(Answer::isCorrect)
                .count() > 1;
    }
}
