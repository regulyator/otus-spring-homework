package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    @EqualsAndHashCode.Include
    private final long id;
    private String fio;
    private Set<Book> books;
}
