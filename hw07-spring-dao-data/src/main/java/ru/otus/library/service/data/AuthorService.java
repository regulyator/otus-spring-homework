package ru.otus.library.service.data;

import ru.otus.library.domain.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorService extends StandardService<Author> {

    Author create(String authorFio);

    Author changeAuthorFio(long idAuthor, String newAuthorFio);

    Collection<Author> getAll(List<Long> authorsIds);
}
