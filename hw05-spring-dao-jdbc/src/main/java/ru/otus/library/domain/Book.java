package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Book {
    @EqualsAndHashCode.Include
    private final long id;
    private long idGenre;
    private String bookName;
    private Set<Author> authors;
}
