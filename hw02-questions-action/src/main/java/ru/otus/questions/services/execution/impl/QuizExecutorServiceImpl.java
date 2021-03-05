package ru.otus.questions.services.execution.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.domain.Question;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.execution.QuizExecutorService;
import ru.otus.questions.services.QuizResultProcessor;
import ru.otus.questions.services.execution.QuizRunner;
import ru.otus.questions.services.util.InputOutputService;

import java.util.List;
import java.util.Map;

@Service
public class QuizExecutorServiceImpl implements QuizExecutorService {

    private final QuizBuilder quizBuilder;
    private final QuizRunner quizRunner;
    private final InputOutputService inputOutputService;
    private final QuizResultProcessor quizResultProcessor;

    @Autowired
    public QuizExecutorServiceImpl(QuizBuilder quizBuilder,
                                   QuizRunner quizRunner,
                                   InputOutputService inputOutputService,
                                   QuizResultProcessor quizResultProcessor) {
        this.quizBuilder = quizBuilder;
        this.quizRunner = quizRunner;
        this.inputOutputService = inputOutputService;
        this.quizResultProcessor = quizResultProcessor;
    }

    @Override
    public void runQuiz() {
        var userName = readInput("Enter first name and last name: ");
        Quiz quiz = quizBuilder.buildQuiz();
        Map<Question, List<Answer>> quizRawResult = quizRunner.runQuizAndCollectAnswers(quiz);
        QuizResult calculatedResult = quizResultProcessor.calculateResults(quizRawResult);
        //inputOutputService.writeOutput(calculatedResult);
    }

    @Override
    public Object getQuizResult() {
        throw new UnsupportedOperationException();
    }

    private String readInput(String message) {
        inputOutputService.writeOutput(message);
        return inputOutputService.readInput();
    }


}
