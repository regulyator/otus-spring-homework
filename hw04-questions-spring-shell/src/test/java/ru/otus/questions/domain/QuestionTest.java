package ru.otus.questions.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {

    @Test
    void getAllAnswers() {
        List<Answer> allAnswers = List.of(new Answer("Alive", true),
                new Answer("Dead", true),
                new Answer("It's a Dog!", false));
        Question question = buildQuestion();
        assertEquals(allAnswers, question.getAnswers());
    }

    @Test
    void getCorrectAnswers() {
        List<Answer> correctAnswers = List.of(new Answer("Alive", true),
                new Answer("Dead", true));
        Question question = buildQuestion();
        assertEquals(correctAnswers, question.getCorrectAnswers());

    }

    @Test
    void getUnCorrectAnswers() {
        List<Answer> inCorrectAnswers = List.of(new Answer("It's a Dog!", false));
        Question question = buildQuestion();
        assertEquals(inCorrectAnswers, question.getInCorrectAnswers());
    }

    private Question buildQuestion() {
        return new Question("Is the cat in the box alive or dead?",
                List.of(new Answer("Alive", true),
                        new Answer("Dead", true),
                        new Answer("It's a Dog!", false)));
    }
}