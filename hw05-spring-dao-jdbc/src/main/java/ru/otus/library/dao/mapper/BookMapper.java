package ru.otus.library.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long bookId = rs.getLong("id");
        String bookName = rs.getString("book_name");

        long authorId = rs.getLong("author_id");
        String authorFio = rs.getString("author_fio");

        long genreId = rs.getLong("genre_id");
        String genreCaption = rs.getString("genre_caption");

        return new Book(bookId, bookName,
                new Author(authorId, authorFio),
                new Genre(genreId, genreCaption));
    }
}