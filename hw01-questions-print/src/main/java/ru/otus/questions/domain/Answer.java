package ru.otus.questions.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Answer {
    private final String text;
    private final boolean correct;
}
