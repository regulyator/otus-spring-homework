package ru.otus.library.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Author {
    @EqualsAndHashCode.Include
    private final long id;
    private final String fio;
    private final Set<Book> books;
}
