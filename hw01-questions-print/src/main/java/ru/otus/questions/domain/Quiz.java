package ru.otus.questions.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Quiz {
    private final List<Question> questions;
}
