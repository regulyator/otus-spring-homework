package ru.otus.questions.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.questions.services.QuizResourceHolder;

@Component
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
