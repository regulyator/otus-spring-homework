package ru.otus.library.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String caption = rs.getString("caption");
        return new Genre(id, caption);
    }
}
