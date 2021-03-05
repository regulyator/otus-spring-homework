package ru.otus.questions.services.execution.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class QuizRunnerImpl implements QuizRunner {

    private final InputOutputService inputOutputServiceConsole;
    private final AnswerReader<Integer> answerReader;

    public QuizRunnerImpl(InputOutputService inputOutputServiceConsole,
                          AnswerReader<Integer> answerReader) {
        this.inputOutputServiceConsole = inputOutputServiceConsole;
        this.answerReader = answerReader;
    }

    @Override
    public Map<Question, List<Answer>> runQuizAndCollectAnswers(Quiz quiz) {
        if (Objects.isNull(quiz)) {
            inputOutputServiceConsole.writeOutput("No Quiz - no fun:(");
        } else {
            Map<Question, List<Answer>> quizUserAnswerMap = new HashMap<>(quiz.getQuestions().size());
            quiz.getQuestions().forEach(question -> printQuestion(quizUserAnswerMap, question));
            return quizUserAnswerMap;
        }
        return null;
    }

    private void printQuestion(Map<Question, List<Answer>> quizUserAnswerMap, Question question) {
        inputOutputServiceConsole.writeOutput(question.getText());

        AtomicInteger answerCounter = new AtomicInteger(0);
        Map<Integer, Answer> answersMap = question.getAnswers()
                .stream().collect(Collectors.toMap(answer -> answerCounter.incrementAndGet(), answer -> answer));
        printAnswers(answersMap);
        List<Integer> convertedAnswersNumbers = answerReader.readUserAnswers(answersMap);

        inputOutputServiceConsole.writeOutput("");

        quizUserAnswerMap.put(question, convertedAnswersNumbers.stream()
                .map(answersMap::get)
                .collect(Collectors.toList()));
    }


    private void printAnswers(Map<Integer, Answer> answersMap) {
        answersMap.forEach((integer, answer) ->
                inputOutputServiceConsole.writeOutput(String.format("%s - %s", integer, answer.getText())));
    }
}
