package ru.otus.questions;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.questions.services.QuizReader;

public class Hw01QuestionsPrintApplication {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuizReader service = context.getBean(QuizReader.class);
        service.readQuiz();
    }

}
