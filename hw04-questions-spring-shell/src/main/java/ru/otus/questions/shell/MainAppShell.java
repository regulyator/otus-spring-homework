package ru.otus.questions.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import ru.otus.questions.services.QuizResultHolder;
import ru.otus.questions.services.QuizResultPrinter;
import ru.otus.questions.services.execution.QuizExecutorService;

@ShellComponent
@ShellCommandGroup("Main commands")
public class MainAppShell {
    private final QuizExecutorService quizExecutorService;
    private final QuizResultHolder quizResultHolder;
    private final QuizResultPrinter quizResultPrinter;
    private String currentUserName = "";

    public MainAppShell(QuizExecutorService quizExecutorService,
                        QuizResultHolder quizResultHolder,
                        QuizResultPrinter quizResultPrinter) {
        this.quizExecutorService = quizExecutorService;
        this.quizResultHolder = quizResultHolder;
        this.quizResultPrinter = quizResultPrinter;
    }

    @ShellMethod(key = "login", value = "Login user")
    public void loginUser(@ShellOption({"UserName"}) String userName) {
        currentUserName = userName;
    }

    @ShellMethod(key = "logout", value = "Logout")
    public void quit() {
        currentUserName = "";
    }

    @ShellMethod(key = "start-quiz", value = "Start quiz")
    public void startQuizForUser() {
        var result = quizExecutorService.runQuiz(currentUserName);
        quizResultHolder.putQuizResult(result);
        quizResultPrinter.printQuizResult(result);
    }

    @ShellMethod(key = "user-quiz-results", value = "Get user results", group = "Statistic commands")
    public void printCurrentUserResults() {
        var userResults = quizResultHolder.getUserResult(currentUserName);
        quizResultPrinter.printQuizResults(userResults);
    }

    @ShellMethod(key = "all-quiz-results", value = "Get all quiz results", group = "Statistic commands")
    public void printAllResults() {
        var allResults = quizResultHolder.getAllResult();
        quizResultPrinter.printQuizResults(allResults);
    }


    @ShellMethodAvailability({"login"})
    public Availability availabilityLoginCheck() {
        return currentUserName.isEmpty() ?
                Availability.available() : Availability.unavailable(" - Already login!");
    }

    @ShellMethodAvailability({"user-quiz-results", "all-quiz-results", "start-quiz", "logout"})
    public Availability availabilityOtherCheck() {
        return !currentUserName.isEmpty() ?
                Availability.available() : Availability.unavailable(" - Please enter name! Use login command");
    }


}
