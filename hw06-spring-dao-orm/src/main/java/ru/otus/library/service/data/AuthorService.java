package ru.otus.library.service.data;

import ru.otus.library.domain.Author;

public interface AuthorService extends StandardService<Author> {

    boolean checkExistById(long id);

    Author create(String newAuthorFio);
}
