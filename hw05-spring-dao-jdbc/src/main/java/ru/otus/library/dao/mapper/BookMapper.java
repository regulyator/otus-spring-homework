package ru.otus.library.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<BookMapper> {
    @Override
    public BookMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
