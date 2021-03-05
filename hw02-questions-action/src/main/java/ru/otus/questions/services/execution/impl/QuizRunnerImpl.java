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
            quiz.getQuestions().forEach(question -> {
                inputOutputServiceConsole.writeOutput(question.getText());
                AtomicInteger answerCounter = new AtomicInteger(0);
                Map<Integer, Answer> answersMap = question.getAnswers()
                        .stream().collect(Collectors.toMap(answer -> answerCounter.incrementAndGet(), answer -> answer));

                printAnswers(answersMap);
                inputOutputServiceConsole.writeOutput("Enter answer number(s) , for multiply answer use comma fo separate: ");
                List<Integer> convertedAnswersNumbers = answerReader.readUserAnswers(answersMap);

                inputOutputServiceConsole.writeOutput("");
                quizUserAnswerMap.put(question, convertedAnswersNumbers.stream()
                        .map(answerNumber -> answersMap.get(answerNumber))
                        .collect(Collectors.toList()));
            });
            return quizUserAnswerMap;
        }
        return null;
    }



    private void printAnswers(Map<Integer, Answer> answersMap) {
        answersMap.forEach((integer, answer) ->
                inputOutputServiceConsole.writeOutput(String.format("%s - %s", integer, answer.getText())));
    }
}
