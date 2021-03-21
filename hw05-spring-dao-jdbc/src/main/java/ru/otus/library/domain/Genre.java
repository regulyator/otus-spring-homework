package ru.otus.library.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre {
    @EqualsAndHashCode.Include
    private final long id;
    private final String name;
}
