package ru.otus.library.service.data.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.NoSuchReferenceIdException;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName(value = "BookServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookServiceImplTest {
    public static final long NEW_ID = 100L;
    public static final Author TEST_NEW_AUTHOR = new Author(10L, "TEST AUTHOR");
    public static final Genre TEST_NEW_GENRE = new Genre(15L, "TEST GENRE");
    public static final String TEST_BOOK_NAME = "TEST BOOK";
    @MockBean
    private BookDao bookDao;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("insert new BOOK with new author and genre and set id")
    void shouldInsertNewBookWithNewAuthorAndGenreAndSetId() {
        when(bookDao.insert(any())).thenReturn(NEW_ID);
        when(authorService.create(anyString())).thenReturn(TEST_NEW_AUTHOR);
        when(genreService.create(anyString())).thenReturn(TEST_NEW_GENRE);
        when(authorService.checkExistById(TEST_NEW_AUTHOR.getId())).thenReturn(true);
        when(genreService.checkExistById(TEST_NEW_GENRE.getId())).thenReturn(true);

        Book newBook = bookService.create(TEST_BOOK_NAME, TEST_NEW_AUTHOR.getFio(), TEST_NEW_GENRE.getCaption());

        assertThat(NEW_ID).isEqualTo(newBook.getId());
        assertThat(TEST_NEW_AUTHOR).usingRecursiveComparison().isEqualTo(newBook.getAuthor());
        assertThat(TEST_NEW_GENRE).usingRecursiveComparison().isEqualTo(newBook.getGenre());
    }

    @Test
    @DisplayName("insert new BOOK with referenced author and genre and set id")
    void shouldInsertNewBookWithReferencedAuthorAndGenreAndSetId() {
        when(bookDao.insert(any())).thenReturn(NEW_ID);
        when(authorService.checkExistById(TEST_NEW_AUTHOR.getId())).thenReturn(true);
        when(genreService.checkExistById(TEST_NEW_GENRE.getId())).thenReturn(true);

        Book newBook = bookService.create(TEST_BOOK_NAME, TEST_NEW_AUTHOR.getId(), TEST_NEW_GENRE.getId());

        assertThat(NEW_ID).isEqualTo(newBook.getId());
        assertThat(TEST_NEW_AUTHOR.getId()).isEqualTo(newBook.getAuthor().getId());
        assertThat(TEST_NEW_GENRE.getId()).isEqualTo(newBook.getGenre().getId());
    }

    @Test
    @DisplayName("throw NoSuchReferenceIdException when create BOOK with missing referenced author and genre")
    void shouldThrowNoSuchReferenceIdExceptionInsertNewBookWithMissingReferenced() {
        when(bookDao.insert(any())).thenReturn(NEW_ID);
        when(authorService.checkExistById(TEST_NEW_AUTHOR.getId())).thenReturn(false);
        when(genreService.checkExistById(TEST_NEW_GENRE.getId())).thenReturn(false);

        assertThatThrownBy(() -> bookService.create(TEST_BOOK_NAME, TEST_NEW_AUTHOR.getId(), TEST_NEW_GENRE.getId()))
                .isInstanceOf(NoSuchReferenceIdException.class);
    }

}