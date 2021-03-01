package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizResourceReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizBuilderCSVTest {
    @Mock
    QuizResourceReader<List<String[]>> quizResourceReader;

    @Test
    void buildQuiz() {
        List<String[]> quiz = List.of(new String[]{"What year is it?", "0:1990"},
                new String[]{"Is the cat in the box alive or dead?", "1:Alive)"});
        Quiz correctQuiz = buildCorrectQuiz();
        when(quizResourceReader.readQuizResource()).thenReturn(quiz);
        QuizBuilder quizBuilder = new QuizBuilderCSV(quizResourceReader);
        Quiz resultQuiz = quizBuilder.buildQuiz();
        assertEquals(correctQuiz, resultQuiz);
    }

    private Quiz buildCorrectQuiz() {
        Question question1 = new Question("What year is it?", List.of(new Answer("1990", false)));
        Question question2 = new Question("Is the cat in the box alive or dead?", List.of(new Answer("Alive", true)));
        return new Quiz(List.of(question1, question2));
    }
}