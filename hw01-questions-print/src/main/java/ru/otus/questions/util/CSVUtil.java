package ru.otus.questions.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class CSVUtil {
    private final static String ANSWER_DELIMITER = ";";
    private final static String ANSWER_RESULT_DELIMITER = ":";
    private final static Pattern IS_ANSWER_RESULT_PATTERN = Pattern.compile("^[1|0]$");


    static String[] getCSVAnswers(String[] questionRaw) {
        return questionRaw[1].split(CSVUtil.ANSWER_DELIMITER);
    }

    static String[] getCSVAnswerWithCorrectMark(String answersRaw) {
        return answersRaw.split(CSVUtil.ANSWER_RESULT_DELIMITER);
    }

    static boolean checkQuestionIsCorrect(String[] questionRaw) {
        return questionRaw.length == 2
                && Objects.nonNull(questionRaw[0])
                && Objects.nonNull(questionRaw[1])
                && !questionRaw[0].trim().isEmpty()
                && !questionRaw[1].trim().isEmpty();
    }

    static boolean checkAnswerIsCorrect(String[] answersRaw) {
        return answersRaw.length == 2
                && Objects.nonNull(answersRaw[0])
                && Objects.nonNull(answersRaw[1])
                && CSVUtil.IS_ANSWER_RESULT_PATTERN.matcher(answersRaw[0].trim()).matches()
                && !answersRaw[1].trim().isEmpty();

    }

}
