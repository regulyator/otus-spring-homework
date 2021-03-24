package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.List;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(Book book) {
        return 0L;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public Book findById(long id) {
        return null;
    }

    @Override
    public List<Book> findAll() {
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
