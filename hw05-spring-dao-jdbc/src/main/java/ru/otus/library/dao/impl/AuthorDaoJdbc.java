package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.dao.mapper.AuthorMapper;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(@NonNull Author author) {
        checkIdForInsert(author.getId());
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource queryParams = new MapSqlParameterSource().addValue("fio", author.getFio());

        namedParameterJdbcOperations.update("insert into author (fio) values (:fio)",
                queryParams, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public void update(@NonNull Author author) {
        checkIdForUpdate(author.getId());
        namedParameterJdbcOperations.update("update author set fio = :fio where id = :id",
                Map.of("id", author.getId(),
                        "fio", author.getFio()));
    }

    @Override
    public Author findById(long id) {
        return namedParameterJdbcOperations.queryForObject("select id, fio from author where id = :id", Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> findAll() {
        return namedParameterJdbcOperations.query("select id, fio from author", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete from author where id = :id", Map.of("id", id));
    }


}
