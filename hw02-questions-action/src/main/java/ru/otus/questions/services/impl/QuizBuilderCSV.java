package ru.otus.questions.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.exception.QuizBuildException;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizResourceReader;
import ru.otus.questions.services.util.QuizRawStructureCheckService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuizBuilderCSV implements QuizBuilder {

    private final static String ANSWER_DELIMITER = ";";
    private final static String ANSWER_RESULT_DELIMITER = ":";

    private final QuizResourceReader<List<String[]>> quizResourceReader;
    private final QuizRawStructureCheckService<String[]> quizRawStructureCheckService;

    @Autowired
    public QuizBuilderCSV(QuizResourceReader<List<String[]>> quizResourceReader,
                          QuizRawStructureCheckService<String[]> quizRawStructureCheckService) {
        this.quizResourceReader = quizResourceReader;
        this.quizRawStructureCheckService = quizRawStructureCheckService;
    }

    @Override
    public Quiz buildQuiz() {
        List<Question> questions = quizResourceReader.readQuizResource().stream()
                .filter(Objects::nonNull)
                .map(this::constructQuestion)
                .collect(Collectors.toList());
        return new Quiz(questions);
    }

    private Question constructQuestion(String[] questionRaw) throws QuizBuildException {
        if (quizRawStructureCheckService.checkRawQuestionIsCorrect(questionRaw)) {
            List<Answer> answers = Arrays.stream(getCSVAnswers(questionRaw))
                    .map(this::constructAnswers)
                    .collect(Collectors.toList());

            return new Question(questionRaw[0], answers);
        } else {
            throw new QuizBuildException("Error when construct quiz question! Incorrect question!");
        }
    }

    private Answer constructAnswers(String answersRaw) throws QuizBuildException {
        final String[] answerSplited = getCSVAnswerWithCorrectMark(answersRaw);
        if (quizRawStructureCheckService.checkRawAnswerIsCorrect(answerSplited)) {
            return new Answer(answerSplited[1], answerSplited[0].trim().equals("1"));
        } else {
            throw new QuizBuildException("Error when construct quiz answer! Incorrect answer!");
        }
    }


    private String[] getCSVAnswers(String[] questionRaw) {
        return questionRaw[1].split(ANSWER_DELIMITER);
    }

    private String[] getCSVAnswerWithCorrectMark(String answersRaw) {
        return answersRaw.split(ANSWER_RESULT_DELIMITER);
    }


}
