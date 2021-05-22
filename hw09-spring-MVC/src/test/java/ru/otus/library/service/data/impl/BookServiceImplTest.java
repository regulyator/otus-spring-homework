package ru.otus.library.service.data.impl;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName(value = "BookServiceImpl should ")
class BookServiceImplTest {
    private static final Book TEST_BOOK = new Book(ObjectId.get().toString(),
            "TEST_BOOK",
            new Genre(ObjectId.get().toString(), "TEST GENRE"),
            new ArrayList<>(), new ArrayList<>());
    private static final Author TEST_NEW_AUTHOR = new Author(ObjectId.get().toString(), "TEST AUTHOR");
    private static final String AUTHOR_ID = ObjectId.get().toString();
    private static final String ID_TEST_BOOK = ObjectId.get().toString();
    private static final Comment TEST_NEW_COMMENT = new Comment("TEST COMMENT");
    private static final String COMMENT_ID = ObjectId.get().toString();
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("add author to Book")
    void shouldAddAuthorToBook() {
        Book expectedBook = TEST_BOOK;
        expectedBook.getAuthors().add(TEST_NEW_AUTHOR);

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(TEST_BOOK));
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

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(sourceBook));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(authorService.getById(AUTHOR_ID)).thenReturn(TEST_NEW_AUTHOR);

        Book actualBook = bookService.removeBookAuthor(ID_TEST_BOOK, AUTHOR_ID);
        assertThat(TEST_BOOK).usingRecursiveComparison().isEqualTo(actualBook);
    }

    @Test
    @DisplayName("add Comment to Book")
    void shouldAddCommentToBook() {
        Book expectedBook = TEST_BOOK;
        expectedBook.getComments().add(TEST_NEW_COMMENT);

        when(bookRepository.findById(anyString())).thenReturn(Optional.of(TEST_BOOK));
        when(bookRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        Book actualBook = bookService.addComment(ID_TEST_BOOK, TEST_NEW_COMMENT.getCaption());
        assertThat(expectedBook).usingRecursiveComparison().isEqualTo(actualBook);
    }

}