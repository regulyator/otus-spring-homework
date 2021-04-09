package ru.otus.library.dao;

import ru.otus.library.domain.Author;

import java.util.Collection;

public interface AuthorDao extends StandardDao<Author> {

    Collection<Author> findAll(Collection<Long> authorsIds);

}
