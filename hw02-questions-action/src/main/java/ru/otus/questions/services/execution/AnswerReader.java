package ru.otus.questions.services.execution;

import ru.otus.questions.domain.Answer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AnswerReader<E> {

    List<E> readUserAnswers(Map<Integer, Answer> answersMap);
}
