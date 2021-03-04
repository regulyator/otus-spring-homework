package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizRunner;
import ru.otus.questions.services.util.InputOutputService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizRunnerImplTest {
    @Mock
    InputOutputService inputOutputService;

    @Test
    void printQuiz() {
        doNothing().when(inputOutputService).writeOutput(anyString());
        Quiz quiz = buildQuiz();
        QuizRunner quizRunner = new QuizRunnerImpl(inputOutputService);
        quizRunner.printQuiz(quiz);
        verify(inputOutputService, times(6)).writeOutput(anyString());
    }

    @Test
    void printNullQuiz() {
        doNothing().when(inputOutputService).writeOutput(anyString());
        QuizRunner quizRunner = new QuizRunnerImpl(inputOutputService);
        quizRunner.printQuiz(null);
        verify(inputOutputService, times(1)).writeOutput(anyString());
    }

    private Quiz buildQuiz() {
        Question question1 = new Question("What year is it?", List.of(new Answer("1990", false)));
        Question question2 = new Question("Is the cat in the box alive or dead?", List.of(new Answer("Alive", true)));
        return new Quiz(List.of(question1, question2));
    }
}