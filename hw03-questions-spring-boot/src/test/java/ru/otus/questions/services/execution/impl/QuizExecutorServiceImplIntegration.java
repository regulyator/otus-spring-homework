package ru.otus.questions.services.execution.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.questions.services.execution.QuizExecutorService;
import ru.otus.questions.services.util.InputOutputComponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuizExecutorServiceImplIntegration {
    @Autowired
    private QuizExecutorService quizExecutorService;
    @MockBean
    private InputOutputComponent inputOutputComponent;

    @Test
    @DisplayName(value = "pass quiz")
    void quizPassed() {
        when(inputOutputComponent.readInput())
                .thenReturn("User")
                .thenReturn("2")
                .thenReturn("1,2")
                .thenReturn("1")
                .thenReturn("3")
                .thenReturn("1");
        final var result = quizExecutorService.runQuiz();
        assertTrue(result.isThresholdPassed());
    }

    @Test
    @DisplayName(value = "fail quiz")
    void quizFailed() {
        when(inputOutputComponent.readInput())
                .thenReturn("User")
                .thenReturn("Q")
                .thenReturn("1,2")
                .thenReturn("1")
                .thenReturn("Q")
                .thenReturn("Q");
        final var result = quizExecutorService.runQuiz();
        assertFalse(result.isThresholdPassed());
    }
}