package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizResultProcessor;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuizResultProcessorImplTest {

    @Test
    void calculateResults() {
        List<Answer> answers = List.of(new Answer("1990", false),
                new Answer("2000", true),
                new Answer("1000", true));
        Question question = new Question("Q", answers);
        List<Answer> correctUserAnswers = List.of(
                new Answer("2000", true),
                new Answer("1000", true));
        List<Answer> inCorrectUserAnswers = List.of(
                new Answer("212", true),
                new Answer("10010", true));
        QuizResultProcessor quizResultProcessor = new QuizResultProcessorImpl(1);
        QuizResult calculatedResultCorrect = quizResultProcessor.calculateResults(Map.of(question, correctUserAnswers));
        assertEquals(1, calculatedResultCorrect.getCorrectAnswersCount());
        assertEquals(0, calculatedResultCorrect.getInCorrectAnswersCount());
        QuizResult calculatedResultInCorrect = quizResultProcessor.calculateResults(Map.of(question, inCorrectUserAnswers));
        assertEquals(0, calculatedResultInCorrect.getCorrectAnswersCount());
        assertEquals(1, calculatedResultInCorrect.getInCorrectAnswersCount());
    }

    @Test
    void calculateResultsCheckThreshold() {
        List<Answer> answers = List.of(new Answer("1990", false),
                new Answer("2000", true),
                new Answer("1000", true));
        Question question = new Question("Q", answers);
        List<Answer> correctUserAnswers = List.of(
                new Answer("2000", true),
                new Answer("1000", true));
        List<Answer> inCorrectUserAnswers = List.of(
                new Answer("212", true),
                new Answer("10010", true));
        QuizResultProcessor quizResultProcessor = new QuizResultProcessorImpl(1);
        QuizResult calculatedResultCorrect = quizResultProcessor.calculateResults(Map.of(question, correctUserAnswers));
        assertTrue(calculatedResultCorrect.isThresholdPassed());
        QuizResult calculatedResultInCorrect = quizResultProcessor.calculateResults(Map.of(question, inCorrectUserAnswers));
        assertFalse(calculatedResultInCorrect.isThresholdPassed());
    }
}