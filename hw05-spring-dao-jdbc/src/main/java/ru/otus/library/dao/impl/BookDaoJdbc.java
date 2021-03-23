package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Book;

import java.util.Collection;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Book insertOrUpdate(@NonNull Book book) {

    }

    @Override
    public Book insert(Book book) {
        return null;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public Book findById(long id) {
        return null;
    }

    @Override
    public Collection<Book> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
