package ru.otus.questions.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.questions.services.QuizResultHolder;
import ru.otus.questions.services.QuizResultPrinter;
import ru.otus.questions.services.execution.QuizExecutorService;

@ShellComponent
public class AppShell {
    private final QuizExecutorService quizExecutorService;
    private final QuizResultHolder quizResultHolder;
    private final QuizResultPrinter quizResultPrinter;
    private String currentUserName = "";

    public AppShell(QuizExecutorService quizExecutorService,
                    QuizResultHolder quizResultHolder,
                    QuizResultPrinter quizResultPrinter) {
        this.quizExecutorService = quizExecutorService;
        this.quizResultHolder = quizResultHolder;
        this.quizResultPrinter = quizResultPrinter;
    }

    @ShellMethod(key = "start-quiz", value = "Start quiz")
    public void startQuizForUser(@ShellOption({"userName", "u"}) String userName) {
        var result = quizExecutorService.runQuiz(userName);
        quizResultHolder.putQuizResult(result);
        quizResultPrinter.printQuizResult(result);
    }

    @ShellMethod(key = "user-quiz-results", value = "Get user results")
    @ShellMethodAvailability
    public void printUserResults(@ShellOption({"userName", "u"}) String userName) {
        var userResults = quizResultHolder.getUserResult(userName);
        quizResultPrinter.printQuizResults(userResults);
    }

    @ShellMethod(key = "all-quiz-results", value = "Get all quiz results")
    public void printAllResults() {
        var allResults = quizResultHolder.getAllResult();
        quizResultPrinter.printQuizResults(allResults);
    }

    @ShellMethodAvailability({"user-quiz-results", "all-quiz-results"})
    public Availability availabilityCheck() {
        return currentUserName.isEmpty() ?
                Availability.available() : Availability.unavailable("you are not connected");
    }


}
