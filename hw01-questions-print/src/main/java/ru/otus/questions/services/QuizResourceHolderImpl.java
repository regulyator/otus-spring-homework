package ru.otus.questions.services;

import org.springframework.core.io.Resource;

public class QuizResourceHolderImpl implements QuizResourceHolder {
    private final Resource quizFileResource;

    public QuizResourceHolderImpl(Resource quizFileResource) {
        this.quizFileResource = quizFileResource;
    }

    @Override
    public Resource getQuizResource() {
        return null;
    }
}
