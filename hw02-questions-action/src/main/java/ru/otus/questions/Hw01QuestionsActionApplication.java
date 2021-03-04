package ru.otus.questions;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.questions.services.QuizExecutorService;

public class Hw01QuestionsActionApplication {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizExecutorService quizExecutorService = context.getBean(QuizExecutorService.class);
        quizExecutorService.runQuiz();

    }

}
