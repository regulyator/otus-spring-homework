package ru.otus.questions.services.util;

public interface QuizRawStructureCheckService<T> {

    boolean checkRawQuestionIsCorrect(T questionRaw);

    boolean checkRawAnswerIsCorrect(T answersRaw);
}
