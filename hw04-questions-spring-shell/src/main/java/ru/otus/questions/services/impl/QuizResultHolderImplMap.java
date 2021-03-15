package ru.otus.questions.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.exception.NoUserResultException;
import ru.otus.questions.services.QuizResultHolder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizResultHolderImplMap implements QuizResultHolder {
    private final Map<String, List<QuizResult>> quizResultMap = new HashMap<>(10);

    @Override
    public void putQuizResult(QuizResult quizResult) {
        quizResultMap.computeIfAbsent(quizResult.getUserName(), s -> addNewUserResult(quizResult));
    }

    @Override
    public List<QuizResult> getUserResult(String userName) {
        if (quizResultMap.containsKey(userName)) {
            return Collections.unmodifiableList(quizResultMap.get(userName));
        } else {
            throw new NoUserResultException("No results found for user: " + userName);
        }
    }

    @Override
    public Collection<QuizResult> getAllResult() {
        var allResults = quizResultMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        if(allResults.isEmpty()){
            throw new NoUserResultException("No one results found!");
        }
        return allResults;
    }

    private List<QuizResult> addNewUserResult(QuizResult quizResult) {
        var userResults = new ArrayList<QuizResult>();
        userResults.add(quizResult);
        return userResults;
    }
}
