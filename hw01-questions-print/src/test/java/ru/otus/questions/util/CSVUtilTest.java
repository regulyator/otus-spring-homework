package ru.otus.questions.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVUtilTest {

    @Test
    void checkGetCSVAnswers() {
        String csvQuestion = "What year is it?,0:1990;1:2021;0:2029";
        String[] answers = {"0:1990", "1:2021", "0:2029"};
        assertArrayEquals(answers, CSVUtil.getCSVAnswers(csvQuestion.split(",")));
    }

    @Test
    void checkGetCSVAnswerWithCorrectMark() {
        String csvAnswerWithMarkRaw = "0:1990";
        String[] answerSplit = {"0", "1990"};
        assertArrayEquals(answerSplit, CSVUtil.getCSVAnswerWithCorrectMark(csvAnswerWithMarkRaw));
    }

    @Test
    void checkQuestionIsCorrect() {
        String csvQuestion = "What year is it?,0:1990;1:2021;0:2029";
        assertTrue(CSVUtil.checkQuestionIsCorrect(csvQuestion.split(",")));
    }

    @Test
    void checkQuestionIsInCorrectEmptyQuestion() {
        String csvQuestion = ",0:1990;1:2021;0:2029";
        assertFalse(CSVUtil.checkQuestionIsCorrect(csvQuestion.split(",")));
    }

    @Test
    void checkQuestionIsInCorrectEmptyAnswer() {
        String csvQuestion = "What year is it?,";
        assertFalse(CSVUtil.checkQuestionIsCorrect(csvQuestion.split(",")));
    }

    @Test
    void checkAnswerIsCorrect() {
        String csvQuestion = "0:1990";
        assertTrue(CSVUtil.checkAnswerIsCorrect(csvQuestion.split(":")));
    }

    @Test
    void checkAnswerIsInCorrectEmptyAnswerMark() {
        String csvQuestion = "1990";
        assertFalse(CSVUtil.checkAnswerIsCorrect(csvQuestion.split(":")));
    }

    @Test
    void checkAnswerIsInCorrectWrongAnswerMark() {
        List<String> incorrectAnswers = List.of("01:1990", "00:1990", "11:1990", "10:1990", "756:1990", "vbn:1990");
        incorrectAnswers.forEach(s -> assertFalse(CSVUtil.checkAnswerIsCorrect(s.split(":"))));
    }
}