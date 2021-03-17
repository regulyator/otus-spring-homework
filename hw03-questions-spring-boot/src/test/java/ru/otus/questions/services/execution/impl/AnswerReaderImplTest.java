package ru.otus.questions.services.execution.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.util.InputOutputLocalizedService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest
class AnswerReaderImplTest {
    @MockBean
    private InputOutputLocalizedService inputOutputLocalizedService;
    @Autowired
    private AnswerReader<Answer> answerReader;

    @Test
    void readUserAnswersOneOption() {
        when(inputOutputLocalizedService.readInputFromUser()).thenReturn("1");
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());
        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertEquals(List.of(new Answer("Alive", true)), readAnswers);
    }

    @Test
    void readUserAnswersMultipleOption() {
        when(inputOutputLocalizedService.readInputFromUser()).thenReturn("1,2");
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());

        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertEquals(List.of(new Answer("Alive", true),
                new Answer("Dead", true)), readAnswers);
    }

    @Test
    void readUserAnswersWrongOption() {
        when(inputOutputLocalizedService.readInputFromUser()).thenReturn("sdfsdf").thenReturn("3");
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());

        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertEquals(List.of(new Answer("It's a Dog!", false)), readAnswers);
        verify(inputOutputLocalizedService, times(2)).readInputFromUser();
    }

    @Test
    void readUserAnswersSkip() {
        when(inputOutputLocalizedService.readInputFromUser()).thenReturn("Q");
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());

        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertTrue(readAnswers.isEmpty());
    }

    private Map<Integer, Answer> buildAnswersMap(List<Answer> answers) {
        AtomicInteger answerCounter = new AtomicInteger(0);
        return answers.stream().collect(Collectors.toMap(answer -> answerCounter.incrementAndGet(), answer -> answer));
    }

    private List<Answer> buildQuestion() {
        return List.of(new Answer("Alive", true),
                new Answer("Dead", true),
                new Answer("It's a Dog!", false));
    }
}