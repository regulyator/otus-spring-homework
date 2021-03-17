package ru.otus.questions.services.execution.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.domain.QuizUserRawResult;
import ru.otus.questions.exception.RunNullQuizException;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuizRunnerImpl implements QuizRunner {

    private final InputOutputComponent inputOutputComponentConsole;
    private final AnswerReader<Answer> answerReader;

    @Autowired
    public QuizRunnerImpl(InputOutputComponent inputOutputComponentConsole,
                          AnswerReader<Answer> answerReader) {
        this.inputOutputComponentConsole = inputOutputComponentConsole;
        this.answerReader = answerReader;
    }

    @Override
    public QuizUserRawResult runQuizAndCollectAnswers(Quiz quiz) {
        if (Objects.isNull(quiz)) {
            throw new RunNullQuizException("NoQuiz");
        } else {
            Map<Question, List<Answer>> quizUserAnswerMap = new HashMap<>(quiz.getQuestions().size());
            quiz.getQuestions().forEach(question -> printQuestion(quizUserAnswerMap, question));
            return new QuizUserRawResult(quizUserAnswerMap);
        }
    }

    private void printQuestion(Map<Question, List<Answer>> quizUserAnswerMap, Question question) {
        inputOutputComponentConsole.writeOutput(question.getText());

        Map<Integer, Answer> answersMap = buildAnswersMap(question.getAnswers());
        printAnswers(answersMap);
        List<Answer> userAnswers = answerReader.readUserAnswers(answersMap);
        quizUserAnswerMap.put(question, userAnswers);
    }

    private Map<Integer, Answer> buildAnswersMap(List<Answer> answers) {
        AtomicInteger answerCounter = new AtomicInteger(0);
        return answers.stream().collect(Collectors.toMap(answer -> answerCounter.incrementAndGet(), answer -> answer));
    }


    private void printAnswers(Map<Integer, Answer> answersMap) {
        answersMap.forEach((integer, answer) ->
                inputOutputComponentConsole.writeOutput(String.format("%s - %s", integer, answer.getText())));
    }
}
