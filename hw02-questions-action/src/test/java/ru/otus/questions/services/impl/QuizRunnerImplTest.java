package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.exception.RunNullQuizException;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.execution.impl.QuizRunnerImpl;
import ru.otus.questions.services.util.InputOutputService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizRunnerImplTest {
    @Mock
    private InputOutputService inputOutputService;
    @Mock
    private AnswerReader<Answer> answerReader;

    @Test
    void printQuiz() {
        Quiz quiz = buildQuiz();
        QuizRunner quizRunner = new QuizRunnerImpl(inputOutputService, answerReader);
        quizRunner.runQuizAndCollectAnswers(quiz);
        verify(inputOutputService, times(6)).writeOutput(anyString());
    }

    @Test
    void printNullQuiz() {
        QuizRunner quizRunner = new QuizRunnerImpl(inputOutputService, answerReader);
        assertThrows(RunNullQuizException.class, () -> quizRunner.runQuizAndCollectAnswers(null));
    }

    private Quiz buildQuiz() {
        Question question1 = new Question("What year is it?", List.of(new Answer("1990", false)));
        Question question2 = new Question("Is the cat in the box alive or dead?", List.of(new Answer("Alive", true)));
        return new Quiz(List.of(question1, question2));
    }
}