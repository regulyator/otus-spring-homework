package ru.otus.questions.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizResultProcessor;

import java.util.ArrayList;
import java.util.Comparator;
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
    public QuizResult calculateResults(Map<Question, List<Answer>> rawQuizResult, String userName) {
        return new QuizResult(userName, countAnswers(rawQuizResult, this::getCorrectPredicateSortedLists),
                countAnswers(rawQuizResult, Predicate.not(this::getCorrectPredicateSortedLists)),
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
                .filter(this::getCorrectPredicateSortedLists)
                .count() >= threshold;
    }

    /**
     * тут вытаскиваем листы, делаем копии сортируем и сравниваем
     *
     * @param questionListEntry оригинал из потока
     * @return предикат
     */
    private boolean getCorrectPredicateSortedLists(Map.Entry<Question, List<Answer>> questionListEntry) {
        List<Answer> correctAnswers = new ArrayList<>(questionListEntry.getKey().getCorrectAnswers());
        correctAnswers.sort(Comparator.comparing(Answer::getText));

        List<Answer> userAnswers = new ArrayList<>(questionListEntry.getValue());
        userAnswers.sort(Comparator.comparing(Answer::getText));

        return correctAnswers.equals(userAnswers);
    }
}
