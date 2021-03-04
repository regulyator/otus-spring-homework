package ru.otus.questions.services.util.impl;

import ru.otus.questions.services.util.QuizRawStructureCheckService;

import java.util.Objects;
import java.util.regex.Pattern;

public class QuizRawStructureCheckServiceCSV implements QuizRawStructureCheckService<String[]> {
    private final static Pattern IS_ANSWER_RESULT_PATTERN = Pattern.compile("^[1|0]$");

    @Override
    public boolean checkRawQuestionIsCorrect(String[] questionRaw) {
        return questionRaw.length == 2
                && Objects.nonNull(questionRaw[0])
                && Objects.nonNull(questionRaw[1])
                && !questionRaw[0].trim().isEmpty()
                && !questionRaw[1].trim().isEmpty();
    }

    @Override
    public boolean checkRawAnswerIsCorrect(String[] answersRaw) {
        return answersRaw.length == 2
                && Objects.nonNull(answersRaw[0])
                && Objects.nonNull(answersRaw[1])
                && IS_ANSWER_RESULT_PATTERN.matcher(answersRaw[0].trim()).matches()
                && !answersRaw[1].trim().isEmpty();
    }
}
