package ru.otus.questions.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizResult {
    private final int correctAnswersCount;
    private final int inCorrectAnswersCount;
    private final boolean thresholdPassed;

    @Override
    public String toString() {
        return String.format("%d - answers are correct, %d - answers are not correct. %s!",
                correctAnswersCount,
                inCorrectAnswersCount,
                thresholdPassed ? "Test passed" : "Test failed");
    }
}
