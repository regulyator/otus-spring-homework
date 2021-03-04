package ru.otus.questions.services.impl;

import org.springframework.core.io.Resource;
import ru.otus.questions.services.QuizResourceHolder;

public class QuizResourceHolderImpl implements QuizResourceHolder {
    private final Resource quizFileResource;

    public QuizResourceHolderImpl(Resource quizFileResource) {
        this.quizFileResource = quizFileResource;
    }

    @Override
    public Resource getQuizResource() {
        return quizFileResource;
    }
}
