package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(Author author) {
        return 0L;
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public Author findById(long id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    private void checkIdForInsert(long id) {
        if (id != 0L) {
            throw new DaoInsertNonEmptyIdException("Call insert for non empty ID field!");
        }
    }

    private void checkIdForUpdate(long id) {
        if (id == 0L) {
            throw new DaoUpdateEmptyIdException("Call update for empty ID field!");
        }
    }
}
