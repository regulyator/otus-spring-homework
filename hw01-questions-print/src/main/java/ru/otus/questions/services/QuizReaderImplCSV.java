package ru.otus.questions.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.questions.domain.Quiz;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizReaderImplCSV implements QuizReader {
    private final QuizResourceHolder quizResourceHolder;

    public QuizReaderImplCSV(QuizResourceHolder quizResourceHolder) {
        this.quizResourceHolder = quizResourceHolder;
    }

    @Override
    public Quiz readQuiz() {
        try (CSVReader reader = new CSVReader(new FileReader(quizResourceHolder.getQuizResource().getFile()))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return new Quiz(Collections.emptyList());
    }
}
