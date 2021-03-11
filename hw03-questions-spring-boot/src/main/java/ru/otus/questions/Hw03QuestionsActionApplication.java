package ru.otus.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.questions.services.execution.QuizExecutorService;

@SpringBootApplication
public class Hw03QuestionsActionApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Hw03QuestionsActionApplication.class, args);
        QuizExecutorService quizExecutorService = context.getBean(QuizExecutorService.class);
        quizExecutorService.runQuiz();

    }

}
