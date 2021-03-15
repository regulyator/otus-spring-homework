package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizResourceReader;
import ru.otus.questions.services.util.QuizRawStructureCheckService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuizBuilderCSVTest {
    @MockBean
    private QuizRawStructureCheckService<String[]> quizRawStructureCheckService;
    @MockBean
    private QuizResourceReader<List<String[]>> quizResourceReader;
    @Autowired
    private QuizBuilder quizBuilder;

    @Test
    void buildQuiz() {
        List<String[]> quiz = List.of(new String[]{"What year is it?", "0:1990"},
                new String[]{"Is the cat in the box alive or dead?", "1:Alive)"});
        Quiz correctQuiz = buildCorrectQuiz();
        when(quizResourceReader.readQuizResource()).thenReturn(quiz);
        when(quizRawStructureCheckService.checkRawAnswerIsCorrect(any())).thenReturn(true);
        when(quizRawStructureCheckService.checkRawQuestionIsCorrect(any())).thenReturn(true);
        Quiz resultQuiz = quizBuilder.buildQuiz();
        assertEquals(correctQuiz, resultQuiz);
    }

    private Quiz buildCorrectQuiz() {
        Question question1 = new Question("What year is it?", List.of(new Answer("1990", false)));
        Question question2 = new Question("Is the cat in the box alive or dead?", List.of(new Answer("Alive", true)));
        return new Quiz(List.of(question1, question2));
    }
}