package ru.otus.questions.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Answer {
    @EqualsAndHashCode.Include
    private final String text;
    private final boolean correct;
}
