package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import ru.otus.questions.services.QuizResourceHolder;
import ru.otus.questions.services.QuizResourceReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class QuizResourceReaderCSVTest {
    @MockBean
    private QuizResourceHolder quizResourceHolder;
    @Autowired
    private QuizResourceReader<List<String[]>> quizResourceReader;

    @Test
    void readQuizResource() {
        String[] question1 = {"What year is it?", "0:1990;1:2021;0:2029"};
        String[] question2 = {"Is the cat in the box alive or dead?", "1:Alive);1:Dead("};
        when(quizResourceHolder.getQuizResource()).thenReturn(new ClassPathResource("questions-test.csv"));
        List<String[]> readResult = quizResourceReader.readQuizResource();
        assertArrayEquals(question1, readResult.get(0));
        assertArrayEquals(question2, readResult.get(1));
    }
}