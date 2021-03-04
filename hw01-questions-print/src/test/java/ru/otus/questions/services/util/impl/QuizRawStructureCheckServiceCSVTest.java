package ru.otus.questions.services.util.impl;

import org.junit.jupiter.api.Test;
import ru.otus.questions.services.util.QuizRawStructureCheckService;
import ru.otus.questions.util.CSVUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizRawStructureCheckServiceCSVTest {

    @Test
    void checkQuestionIsCorrect() {
        QuizRawStructureCheckService<String[]> quizRawStructureCheckService = new QuizRawStructureCheckServiceCSV();
        String csvQuestion = "What year is it?,0:1990;1:2021;0:2029";
        assertTrue(quizRawStructureCheckService.checkRawQuestionIsCorrect(csvQuestion.split(",")));
    }

    @Test
    void checkQuestionIsInCorrectEmptyQuestion() {
        QuizRawStructureCheckService<String[]> quizRawStructureCheckService = new QuizRawStructureCheckServiceCSV();
        String csvQuestion = ",0:1990;1:2021;0:2029";
        assertFalse(quizRawStructureCheckService.checkRawQuestionIsCorrect(csvQuestion.split(",")));
    }

    @Test
    void checkQuestionIsInCorrectEmptyAnswer() {
        QuizRawStructureCheckService<String[]> quizRawStructureCheckService = new QuizRawStructureCheckServiceCSV();
        String csvQuestion = "What year is it?,";
        assertFalse(quizRawStructureCheckService.checkRawQuestionIsCorrect(csvQuestion.split(",")));
    }

    @Test
    void checkAnswerIsCorrect() {
        QuizRawStructureCheckService<String[]> quizRawStructureCheckService = new QuizRawStructureCheckServiceCSV();
        String csvQuestion = "0:1990";
        assertTrue(quizRawStructureCheckService.checkRawQuestionIsCorrect(csvQuestion.split(":")));
    }

    @Test
    void checkAnswerIsInCorrectEmptyAnswerMark() {
        QuizRawStructureCheckService<String[]> quizRawStructureCheckService = new QuizRawStructureCheckServiceCSV();
        String csvQuestion = "1990";
        assertFalse(quizRawStructureCheckService.checkRawAnswerIsCorrect(csvQuestion.split(":")));
    }

    @Test
    void checkAnswerIsInCorrectWrongAnswerMark() {
        QuizRawStructureCheckService<String[]> quizRawStructureCheckService = new QuizRawStructureCheckServiceCSV();
        List<String> incorrectAnswers = List.of("01:1990", "00:1990", "11:1990", "10:1990", "756:1990", "vbn:1990");
        incorrectAnswers.forEach(s -> assertFalse(quizRawStructureCheckService.checkRawAnswerIsCorrect(s.split(":"))));
    }
}