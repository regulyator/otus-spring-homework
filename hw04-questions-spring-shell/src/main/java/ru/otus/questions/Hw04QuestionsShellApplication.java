package ru.otus.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.questions.services.execution.QuizExecutorService;

@SpringBootApplication
public class Hw04QuestionsShellApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Hw04QuestionsShellApplication.class, args);
        QuizExecutorService quizExecutorService = context.getBean(QuizExecutorService.class);
        quizExecutorService.runQuiz();

    }

}
