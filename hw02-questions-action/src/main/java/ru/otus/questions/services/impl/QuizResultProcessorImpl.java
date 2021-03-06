package ru.otus.questions.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizResultProcessor;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class QuizResultProcessorImpl implements QuizResultProcessor {
    private final int threshold;

    public QuizResultProcessorImpl(@Value("${ru.otus.hw.test.threshold}") int threshold) {
        this.threshold = threshold;
    }

    @Override
    public QuizResult calculateResults(Map<Question, List<Answer>> rawQuizResult) {
        return new QuizResult(countAnswers(rawQuizResult, questionListEntry -> questionListEntry.getKey().getCorrectAnswers().equals(questionListEntry.getValue())),
                countAnswers(rawQuizResult, Predicate.not(questionListEntry -> questionListEntry.getKey().getCorrectAnswers().equals(questionListEntry.getValue()))),
                calculateTestPasses(rawQuizResult));
    }

    private int countAnswers(Map<Question, List<Answer>> rawQuizResult, Predicate<Map.Entry<Question, List<Answer>>> answerPredicate) {
        return Math.toIntExact(rawQuizResult.entrySet()
                .stream()
                .filter(answerPredicate)
                .count());
    }

    private boolean calculateTestPasses(Map<Question, List<Answer>> rawQuizResult) {
        return rawQuizResult.entrySet()
                .stream()
                .filter(questionListEntry -> questionListEntry.getKey().getCorrectAnswers().containsAll(questionListEntry.getValue()))
                .count() >= threshold;
    }
}
