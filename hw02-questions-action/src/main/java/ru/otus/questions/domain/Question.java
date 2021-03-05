package ru.otus.questions.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Question {
    @EqualsAndHashCode.Include
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

    public List<Answer> getCorrectAnswers() {
        return answers.stream().filter(Answer::isCorrect).collect(Collectors.toList());
    }

    public List<Answer> getUnCorrectAnswers() {
        return answers.stream().filter(Predicate.not(Answer::isCorrect)).collect(Collectors.toList());
    }
}
