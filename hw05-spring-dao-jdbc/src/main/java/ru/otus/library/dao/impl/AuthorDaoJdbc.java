package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import java.util.Collection;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Author insertOrUpdate(@NonNull Author author) {

    }

    @Override
    public Author insert(Author author) {
        return null;
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public Author findById(long id) {
        return null;
    }

    @Override
    public Collection<Author> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
