package ru.otus.library.service.data;

import ru.otus.library.domain.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorService extends StandardService<Author> {

    List<Author> getAll(Collection<String> authorsIds);
}
