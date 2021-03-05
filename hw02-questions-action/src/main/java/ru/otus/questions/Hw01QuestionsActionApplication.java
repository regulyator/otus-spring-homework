package ru.otus.questions;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.questions.services.QuizExecutorService;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class Hw01QuestionsActionApplication {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Hw01QuestionsActionApplication.class);
        QuizExecutorService quizExecutorService = context.getBean(QuizExecutorService.class);
        quizExecutorService.runQuiz();

    }

}
