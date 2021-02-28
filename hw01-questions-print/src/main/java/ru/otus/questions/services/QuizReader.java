package ru.otus.questions.services;

import org.springframework.core.io.Resource;
import ru.otus.questions.domain.Quiz;

public interface QuizReader {

    Quiz readQuiz();
}
