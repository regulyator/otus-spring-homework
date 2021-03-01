package ru.otus.questions;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.questions.domain.Quiz;
import ru.otus.questions.services.QuizBuilder;
import ru.otus.questions.services.QuizRunner;

public class Hw01QuestionsPrintApplication {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizBuilder quizBuilder = context.getBean(QuizBuilder.class);
        QuizRunner quizRunner = context.getBean(QuizRunner.class);
        Quiz quiz = quizBuilder.buildQuiz();
        quizRunner.printQuiz(quiz);

    }

}
