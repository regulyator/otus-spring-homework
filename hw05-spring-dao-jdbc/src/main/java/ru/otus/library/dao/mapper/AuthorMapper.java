package ru.otus.library.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String fio = rs.getString("fio");
        return new Author(id, fio);
    }
}
