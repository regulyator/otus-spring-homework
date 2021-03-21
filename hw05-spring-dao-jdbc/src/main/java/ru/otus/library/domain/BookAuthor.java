package ru.otus.library.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookAuthor {
    @EqualsAndHashCode.Include
    private final long idBook;
    private final long idAuthor;
}
