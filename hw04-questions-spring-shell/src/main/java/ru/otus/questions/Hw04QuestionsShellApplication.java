package ru.otus.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.questions.services.execution.QuizExecutorService;

@SpringBootApplication
public class Hw04QuestionsShellApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw04QuestionsShellApplication.class, args);
    }

}
