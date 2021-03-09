package ru.otus.questions.services.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.exception.QuizRawReadException;
import ru.otus.questions.services.QuizResourceHolder;
import ru.otus.questions.services.QuizResourceReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class QuizResourceReaderCSV implements QuizResourceReader<List<String[]>> {
    private final QuizResourceHolder quizResourceHolder;

    @Autowired
    public QuizResourceReaderCSV(QuizResourceHolder quizResourceHolder) {
        this.quizResourceHolder = quizResourceHolder;
    }

    @Override
    public List<String[]> readQuizResource() {
        try (InputStream is = quizResourceHolder.getQuizResource().getInputStream();
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new QuizRawReadException("Error when read quiz csv resource!", e);
        }
    }
}
