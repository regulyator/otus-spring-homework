package ru.otus.library.service.data.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.CommentService;
import ru.otus.library.service.data.GenreService;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName(value = "BookServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookServiceImplTest {
    private static final Book TEST_BOOK = new Book(100L, "TEST_BOOK", new Genre(10L, "TEST GENRE"), new HashSet<>(), new HashSet<>());
    private static final Author TEST_NEW_AUTHOR = new Author(10L, "TEST AUTHOR");
    private static final Comment TEST_NEW_COMMENT = new Comment(20L, "TEST COMMENT");
    private static final String NEW_COMMENT_CAPTION = "TEST COMMENT";
    private static final long AUTHOR_ID = 10L;
    private static final long COMMENT_ID = 20L;
    private static final long ID_TEST_BOOK = 100L;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;
    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("add author to Book")
    void shouldAddAuthorToBook() {
        Book expectedBook = TEST_BOOK;
        expectedBook.getAuthors().add(TEST_NEW_AUTHOR);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(TEST_BOOK));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(authorService.getById(AUTHOR_ID)).thenReturn(TEST_NEW_AUTHOR);

        Book actualBook = bookService.addBookAuthor(ID_TEST_BOOK, AUTHOR_ID);
        assertThat(expectedBook).usingRecursiveComparison().isEqualTo(actualBook);
    }

    @Test
    @DisplayName("remove author from Book")
    void shouldRemoveAuthorFromBook() {

        Book sourceBook = TEST_BOOK;
        sourceBook.getAuthors().add(TEST_NEW_AUTHOR);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(sourceBook));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(authorService.getById(AUTHOR_ID)).thenReturn(TEST_NEW_AUTHOR);

        Book actualBook = bookService.removeBookAuthor(ID_TEST_BOOK, AUTHOR_ID);
        assertThat(TEST_BOOK).usingRecursiveComparison().isEqualTo(actualBook);
    }

    @Test
    @DisplayName("remove comment from Book")
    void shouldRemoveCommentFromBook() {

        Book sourceBook = TEST_BOOK;
        sourceBook.getComments().add(TEST_NEW_COMMENT);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(sourceBook));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(commentService.getById(COMMENT_ID)).thenReturn(TEST_NEW_COMMENT);

        Book actualBook = bookService.removeBookComment(ID_TEST_BOOK, COMMENT_ID);
        assertThat(TEST_BOOK).usingRecursiveComparison().isEqualTo(actualBook);
    }

}