package ru.otus.questions.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.questions.services.QuizResourceHolder;

@Service
public class QuizResourceHolderImpl implements QuizResourceHolder {
    private final Resource quizFileResource;

    public QuizResourceHolderImpl(@Value("${ru.otus.hw.test.file}") Resource quizFileResource) {
        this.quizFileResource = quizFileResource;
    }

    @Override
    public Resource getQuizResource() {
        return quizFileResource;
    }
}
