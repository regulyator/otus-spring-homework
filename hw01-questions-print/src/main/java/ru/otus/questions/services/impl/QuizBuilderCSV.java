package ru.otus.questions.services.impl;

import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizResourceReader;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.otus.questions.util.CSVUtil.*;

public class QuizBuilderCSV implements QuizBuilder {

    private final QuizResourceReader<List<String[]>> quizResourceReader;

    public QuizBuilderCSV(QuizResourceReader<List<String[]>> quizResourceReader) {
        this.quizResourceReader = quizResourceReader;
    }

    @Override
    public Quiz buildQuiz() {
        List<Question> questions = quizResourceReader.readQuizResource().stream()
                .filter(Objects::nonNull)
                .map(this::constructQuestion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new Quiz(questions);
    }

    private Question constructQuestion(String[] questionRaw) {
        if (checkQuestionIsCorrect(questionRaw)) {
            List<Answer> answers = Arrays.stream(getCSVAnswers(questionRaw))
                    .map(this::constructAnswers)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return new Question(questionRaw[0], answers);
        } else {
            return null;
        }
    }

    private Answer constructAnswers(String answersRaw) {
        final String[] answerSplited = getCSVAnswerWithCorrectMark(answersRaw);
        if (checkAnswerIsCorrect(answerSplited)) {
            return new Answer(answerSplited[1], answerSplited[0].trim().equals("1"));
        } else {
            return null;
        }
    }


}
