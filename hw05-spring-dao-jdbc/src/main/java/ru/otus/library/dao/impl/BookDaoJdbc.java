package ru.otus.library.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.BookDao;
import ru.otus.library.dao.mapper.BookMapper;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ConstantConditions")
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public boolean isExistById(long id) {
        return namedParameterJdbcOperations.queryForObject("select count(id) from book where id = :id",
                Map.of("id", id), Integer.class) > 0;
    }

    @Override
    public long insert(@NonNull Book book) {
        checkIdForInsert(book.getId());
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource queryParams = new MapSqlParameterSource()
                .addValue("bookName", book.getBookName())
                .addValue("idGenre", book.getGenre().getId())
                .addValue("idAuthor", book.getAuthor().getId());

        namedParameterJdbcOperations.update("insert into book (book_name, id_genre, id_author) values (:bookName,:idGenre,:idAuthor)",
                queryParams, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public void update(@NonNull Book book) {
        checkIdForUpdate(book.getId());

        namedParameterJdbcOperations.update("update book set book_name = :bookName, id_genre = :idGenre, id_author = :idAuthor  where id = :id",
                Map.of("id", book.getId(),
                        "bookName", book.getBookName(),
                        "idGenre", book.getGenre().getId(),
                        "idAuthor", book.getAuthor().getId()));
    }

    @Override
    public Book findById(long id) {
        return namedParameterJdbcOperations.queryForObject("select b.id, b.book_name, " +
                "g.id as genre_id, g.caption as genre_caption, " +
                "a.id as author_id, a.fio as author_fio " +
                "from book b " +
                "left join genre g on g.id = b.id_genre " +
                "left join author a on a.id = id_author where b.id = :id", Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations.query("select b.id, b.book_name, " +
                "g.id as genre_id, g.caption as genre_caption, " +
                "a.id as author_id, a.fio as author_fio " +
                "from book b " +
                "left join genre g on g.id = b.id_genre " +
                "left join author a on a.id = id_author", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete from book where id = :id", Map.of("id", id));
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
