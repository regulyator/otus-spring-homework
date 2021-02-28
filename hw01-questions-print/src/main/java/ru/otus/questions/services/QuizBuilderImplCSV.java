package ru.otus.questions.services;

import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QuizBuilderImplCSV implements QuizBuilder {
    private final static String ANSWER_DELIMITER = ";";
    private final static String ANSWER_RESULT_DELIMITER = ":";
    private final Pattern IS_ANSWER_RESULT_PATTERN = Pattern.compile("^[1|0]$");


    @Override
    public Quiz buildQuiz(List<String[]> quizSource) {
        List<Question> questions = quizSource.stream()
                .filter(Objects::nonNull)
                .map(this::constructQuestion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new Quiz(questions);
    }

    private Question constructQuestion(String[] questionRaw) {
        if (checkQuestionIsCorrect(questionRaw)) {
            List<Answer> collect = Arrays.stream(questionRaw[1].split(ANSWER_DELIMITER))
                    .map(this::constructAnswers)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private Answer constructAnswers(String answersRaw) {
        final String[] answerSplited = answersRaw.split(ANSWER_RESULT_DELIMITER);
        if (checkAnswerIsCorrect(answerSplited)) {
            return new Answer(answerSplited[1], answerSplited[0].trim().equals("1"));
        } else {
            return null;
        }
    }

    private boolean checkQuestionIsCorrect(String[] questionRaw) {
        return questionRaw.length == 2
                && Objects.nonNull(questionRaw[0])
                && Objects.nonNull(questionRaw[1])
                && !questionRaw[0].trim().isEmpty()
                && !questionRaw[1].trim().isEmpty();
    }

    private boolean checkAnswerIsCorrect(String[] answersRaw) {
        return answersRaw.length == 2
                && Objects.nonNull(answersRaw[0])
                && Objects.nonNull(answersRaw[1])
                && IS_ANSWER_RESULT_PATTERN.matcher(answersRaw[0].trim()).matches()
                && !answersRaw[1].trim().isEmpty();

    }
}
