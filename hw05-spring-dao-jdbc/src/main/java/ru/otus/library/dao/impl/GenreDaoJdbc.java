package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.dao.mapper.GenreMapper;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.List;
import java.util.Objects;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(@NonNull Genre genre) {
        checkIdForInsert(genre.getId());
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("caption", genre.getCaption());

        namedParameterJdbcOperations.update("insert into genre (caption) values (:caption)",
                queryParams, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public void update(@NonNull Genre genre) {
        checkIdForUpdate(genre.getId());
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("id", genre.getId());
        queryParams.addValue("caption", genre.getCaption());

        namedParameterJdbcOperations.update("update genre set caption = :caption where id = :id",
                queryParams);

    }

    @Override
    public Genre findById(long id) {
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, caption from genre where id = :id",
                queryParams, new GenreMapper());
    }

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcOperations.query("select id, caption from genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("id", id);
        namedParameterJdbcOperations.update("delete from genre where id = :id", queryParams);
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
