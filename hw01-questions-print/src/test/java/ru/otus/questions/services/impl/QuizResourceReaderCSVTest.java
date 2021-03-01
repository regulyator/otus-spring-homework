package ru.otus.questions.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import ru.otus.questions.services.QuizResourceHolder;
import ru.otus.questions.services.QuizResourceReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizResourceReaderCSVTest {
    @Mock
    QuizResourceHolder quizResourceHolder;

    @Test
    void readQuizResource() {
        String[] question1 = {"What year is it?", "0:1990;1:2021;0:2029"};
        String[] question2 = {"Is the cat in the box alive or dead?", "1:Alive);1:Dead("};
        when(quizResourceHolder.getQuizResource()).thenReturn(new ClassPathResource("questions-test.csv"));
        QuizResourceReader<List<String[]>> quizResourceReader = new QuizResourceReaderCSV(quizResourceHolder);
        List<String[]> readResult = quizResourceReader.readQuizResource();
        assertArrayEquals(question1, readResult.get(0));
        assertArrayEquals(question2, readResult.get(1));
    }
}