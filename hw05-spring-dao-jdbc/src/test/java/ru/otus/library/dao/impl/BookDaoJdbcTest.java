package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("BookDaoJdbcTest should ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {


    private static final long EXIST_ID_BOOK = 1;
    private static final long EMPTY_ID_BOOK = 0L;
    private static final String NEW_BOOK_NAME = "New book test";
    private static final String EXIST_BOOK_NAME = "Blindsight book test";

    private static final String UPDATED_BOOK_NAME = "HORROR TEST BOOK UPDATED";
    private static final Author UPDATED_BOOK_AUTHOR = new Author(2, "Robert Hainline test author");
    private static final Genre UPDATED_BOOK_GENRE = new Genre(2, "Fantasy test genre");

    private static final long EXIST_ID_AUTHOR = 1;
    private static final String EXIST_FIO_AUTHOR = "Peter Watts test author";
    private static final long EXIST_ID_GENRE = 3;
    private static final String EXIST_CAPTION_GENRE = "Sci-Fi test genre";
    @Autowired
    @SpyBean
    private BookDao bookDao;

    @DisplayName("return true if BOOK id exist")
    @Test
    void shouldReturnTrueIfBookIdExist() {
        assertThat(true).isEqualTo(bookDao.isExistById(EXIST_ID_BOOK));
    }

    @DisplayName("return false if BOOK id not exist")
    @Test
    void shouldReturnTrueIfBookIdNotExist() {
        assertThat(false).isEqualTo(bookDao.isExistById(EMPTY_ID_BOOK));
    }

    @DisplayName("insert BOOK with generated ID")
    @Test
    void shouldInsertBookAndGenerateId() {
        Book expectedBook = getNewBook();
        long generatedId = bookDao.insert(expectedBook);
        Book actualBook = bookDao.findById(generatedId);

        assertThat(generatedId).isNotEqualTo(expectedBook.getId());

        expectedBook.setId(generatedId);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        verify(bookDao, times(1)).insert(expectedBook);
    }

    @DisplayName("return BOOK by ID")
    @Test
    void shouldReturnBookById() {
        Book expectedBook = getExistBook();
        Book actualBook = bookDao.findById(EXIST_ID_BOOK);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        verify(bookDao, times(1)).findById(EXIST_ID_BOOK);
    }

    @DisplayName("return all BOOK")
    @Test
    void shouldReturnAllBook() {
        Collection<Book> expectedBooks = getAllExistingBook();
        Collection<Book> actualBooks = bookDao.findAll();

        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
        verify(bookDao, times(1)).findAll();
    }

    @DisplayName("update BOOK")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(EXIST_ID_BOOK, UPDATED_BOOK_NAME, UPDATED_BOOK_AUTHOR, UPDATED_BOOK_GENRE);

        Book storedBook = bookDao.findById(EXIST_ID_BOOK);
        assertThat(storedBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
        storedBook.setBookName(UPDATED_BOOK_NAME);
        storedBook.setAuthor(UPDATED_BOOK_AUTHOR);
        storedBook.setGenre(UPDATED_BOOK_GENRE);
        bookDao.update(storedBook);
        Book storedUpdatedBook = bookDao.findById(EXIST_ID_BOOK);

        assertThat(storedUpdatedBook).usingRecursiveComparison().isEqualTo(expectedBook);
        verify(bookDao, times(0)).insert(any());
        verify(bookDao, times(1)).update(expectedBook);
    }

    @DisplayName("delete BOOK by ID")
    @Test
    void shouldDeleteBook() {
        assertThatCode(() -> bookDao.findById(EXIST_ID_BOOK))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXIST_ID_BOOK);

        assertThatThrownBy(() -> bookDao.findById(EXIST_ID_BOOK))
                .isInstanceOf(EmptyResultDataAccessException.class);
        verify(bookDao, times(1)).deleteById(EXIST_ID_BOOK);
    }

    @DisplayName("throw DaoInsertNonEmptyIdException when insert BOOK with non 0L ID")
    @Test
    void shouldThrowExceptionWhenTryInsertWithNonEmptyId() {
        Book insertedBook = getExistBook();
        Collection<Book> expectedBooks = getAllExistingBook();

        assertThatThrownBy(() -> bookDao.insert(insertedBook))
                .isInstanceOf(DaoInsertNonEmptyIdException.class);
        Collection<Book> actualBooks = bookDao.findAll();

        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @DisplayName("throw DaoUpdateEmptyIdException when update BOOK with 0L ID")
    @Test
    void shouldThrowExceptionWhenTryUpdateWithEmptyId() {
        Book updatedBook = getNewBook();
        Collection<Book> expectedBooks = getAllExistingBook();

        assertThatThrownBy(() -> bookDao.update(updatedBook))
                .isInstanceOf(DaoUpdateEmptyIdException.class);
        Collection<Book> actualBooks = bookDao.findAll();

        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    private Collection<Book> getAllExistingBook() {
        return List.of(
                new Book(1, "Blindsight book test",
                        new Author(1, "Peter Watts test author"),
                        new Genre(3, "Sci-Fi test genre")),
                new Book(2, "The Moon Is a Harsh Mistress book test",
                        new Author(2, "Robert Hainline test author"),
                        new Genre(3, "Sci-Fi test genre")),
                new Book(3, "Prisoners of Power book test",
                        new Author(3, "Arkady and Boris Strugatsky test author"),
                        new Genre(2, "Fantasy test genre"))
        );
    }

    private Book getNewBook() {
        return new Book(EMPTY_ID_BOOK,
                NEW_BOOK_NAME,
                getExistAuthor(),
                getExistGenre());
    }

    private Book getExistBook() {
        return new Book(EXIST_ID_BOOK,
                EXIST_BOOK_NAME,
                getExistAuthor(),
                getExistGenre());
    }

    private Author getExistAuthor() {
        return new Author(EXIST_ID_AUTHOR, EXIST_FIO_AUTHOR);
    }

    private Genre getExistGenre() {
        return new Genre(EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
    }

}