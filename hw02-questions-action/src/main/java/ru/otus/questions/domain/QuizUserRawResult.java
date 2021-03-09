package ru.otus.questions.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class QuizUserRawResult {
    private final Map<Question, List<Answer>> userAnswersMap;
}
