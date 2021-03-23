package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import java.util.Collection;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Genre insertOrUpdate(@NonNull Genre genre) {
        if (genre.getId() == 0L) {
            return insert(genre);
        } else {
            update(genre);
            return genre;
        }
    }

    @Override
    public Genre insert(Genre genre) {

        return null;
    }

    @Override
    public void update(Genre genre) {

    }

    @Override
    public Genre findById(long id) {
        return null;
    }

    @Override
    public Collection<Genre> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
