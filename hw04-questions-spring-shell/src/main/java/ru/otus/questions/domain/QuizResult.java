package ru.otus.questions.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizResult {
    private final String userName;
    private final int correctAnswersCount;
    private final int inCorrectAnswersCount;
    private final boolean thresholdPassed;
}
