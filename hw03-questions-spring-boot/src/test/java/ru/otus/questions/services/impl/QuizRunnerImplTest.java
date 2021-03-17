package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.exception.RunNullQuizException;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputComponent;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
class QuizRunnerImplTest {
    @MockBean
    private InputOutputComponent inputOutputComponent;
    @MockBean
    private AnswerReader<Answer> answerReader;
    @Autowired
    private QuizRunner quizRunner;

    @Test
    void printQuiz() {
        Quiz quiz = buildQuiz();
        quizRunner.runQuizAndCollectAnswers(quiz);
        verify(inputOutputComponent, times(4)).writeOutput(anyString());
    }

    @Test
    void printNullQuiz() {
        assertThrows(RunNullQuizException.class, () -> quizRunner.runQuizAndCollectAnswers(null));
    }

    private Quiz buildQuiz() {
        Question question1 = new Question("What year is it?", List.of(new Answer("1990", false)));
        Question question2 = new Question("Is the cat in the box alive or dead?", List.of(new Answer("Alive", true)));
        return new Quiz(List.of(question1, question2));
    }
}