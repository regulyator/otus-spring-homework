package ru.otus.questions.services.execution.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.util.InputOutputService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class AnswerReaderImplTest {
    @Mock
    private InputOutputService inputOutputService;

    @Test
    void readUserAnswersOneOption() {
        when(inputOutputService.readInput()).thenReturn("1");
        AnswerReader<Answer> answerReader = new AnswerReaderImpl(inputOutputService);
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());

        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertEquals(List.of(new Answer("Alive", true)), readAnswers);
    }

    @Test
    void readUserAnswersMultipleOption() {
        when(inputOutputService.readInput()).thenReturn("1,2");
        AnswerReader<Answer> answerReader = new AnswerReaderImpl(inputOutputService);
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());

        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertEquals(List.of(new Answer("Alive", true),
                new Answer("Dead", true)), readAnswers);
    }

    @Test
    void readUserAnswersWrongOption() {
        when(inputOutputService.readInput()).thenReturn("sdfsdf").thenReturn("3");
        AnswerReader<Answer> answerReader = new AnswerReaderImpl(inputOutputService);
        Map<Integer, Answer> answerMap = buildAnswersMap(buildQuestion());

        List<Answer> readAnswers = answerReader.readUserAnswers(answerMap);
        assertEquals(List.of(new Answer("It's a Dog!", false)), readAnswers);
        verify(inputOutputService, times(2)).readInput();
    }

    @Test
    void readUserAnswersSkip() {
        when(inputOutputService.readInput()).thenReturn("Q");
        AnswerReader<Answer> answerReader = new AnswerReaderImpl(inputOutputService);
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