package ru.otus.questions.services.execution.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuizRunnerImpl implements QuizRunner {

    private final InputOutputService inputOutputServiceConsole;
    private final AnswerReader<Answer> answerReader;

    public QuizRunnerImpl(InputOutputService inputOutputServiceConsole,
                          AnswerReader<Answer> answerReader) {
        this.inputOutputServiceConsole = inputOutputServiceConsole;
        this.answerReader = answerReader;
    }

    @Override
    public Map<Question, List<Answer>> runQuizAndCollectAnswers(Quiz quiz) {
        if (Objects.isNull(quiz)) {
            inputOutputServiceConsole.writeOutput("No Quiz - no fun:(");
            return Collections.emptyMap();
        } else {
            Map<Question, List<Answer>> quizUserAnswerMap = new HashMap<>(quiz.getQuestions().size());
            quiz.getQuestions().forEach(question -> printQuestion(quizUserAnswerMap, question));
            return quizUserAnswerMap;
        }
    }

    private void printQuestion(Map<Question, List<Answer>> quizUserAnswerMap, Question question) {
        inputOutputServiceConsole.writeOutput(question.getText());

        Map<Integer, Answer> answersMap = buildAnswersMap(question.getAnswers());
        printAnswers(answersMap);
        List<Answer> userAnswers = answerReader.readUserAnswers(answersMap);
        quizUserAnswerMap.put(question, userAnswers);

        inputOutputServiceConsole.writeOutput("");
    }

    private Map<Integer, Answer> buildAnswersMap(List<Answer> answers) {
        AtomicInteger answerCounter = new AtomicInteger(0);
        return answers.stream().collect(Collectors.toMap(answer -> answerCounter.incrementAndGet(), answer -> answer));
    }


    private void printAnswers(Map<Integer, Answer> answersMap) {
        answersMap.forEach((integer, answer) ->
                inputOutputServiceConsole.writeOutput(String.format("%s - %s", integer, answer.getText())));
    }
}
