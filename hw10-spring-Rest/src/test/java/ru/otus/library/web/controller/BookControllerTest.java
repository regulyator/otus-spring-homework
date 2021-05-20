package ru.otus.library.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("BookController should")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
    private static final String REDIRECT_BOOKS = "redirect:/books/";
    private static final String BOOKS_URL = "/books";
    private static final String BOOKS_VIEW = "Books";
    private static final String BOOK_VIEW = "Book";
    private static final String BOOK_ID = "bId";
    private static final Genre GENRE = new Genre("GENRE_ID", "GENRE_CAPTION");
    private static final Book BOOK = new Book(BOOK_ID,
            "BOOK_NAME",
            GENRE,
            Collections.emptyList(),
            Collections.emptyList());
    private static final BookDto BOOK_DTO = new BookDto(BOOK);
    private static final String COMMENT_TEXT = "COMMENT_TEXT";
    private static final String COMMENT_ID = "cId";
    private static final String AUTHOR_ID = "aId";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookService bookService;

    @DisplayName("get all books")
    @Test
    void shouldGetAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(BOOKS_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(BOOKS_VIEW));

        verify(bookService, times(1)).getAllDto();
    }

    @DisplayName("get book by id")
    @Test
    void shouldGetBookById() throws Exception {
        when(bookService.getByIdDto(BOOK_ID)).thenReturn(BOOK_DTO);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/{bookId}", BOOK_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(BOOK_VIEW))
                .andExpect(model().attribute("bookDto", BOOK_DTO))
                .andExpect(model().attribute("genres", Collections.emptyList()))
                .andExpect(model().attribute("authors", Collections.emptyList()));

        verify(bookService, times(1)).getByIdDto(BOOK_ID);
        verify(authorService, times(1)).getAll();
    }

    @DisplayName("return new bookDto")
    @Test
    void shouldReturnNewBookDto() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(BOOK_VIEW))
                .andExpect(model().attribute("genres", Collections.emptyList()))
                .andExpect(model().attribute("authors", Collections.emptyList()))
                .andReturn();

        assertThat(result.getModelAndView().getModel().get("bookDto")).isNotNull();
    }

    @DisplayName("create book")
    @Test
    void shouldCreateBook() throws Exception {
        BookDto bookDto = BOOK_DTO;
        bookDto.setId("");

        when(bookService.createOrUpdate(any(Book.class))).thenReturn(BOOK);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/books")
                .flashAttr("bookDto", bookDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS + null));

        verify(bookService, times(1)).createOrUpdate(any());
        verify(bookService, times(1)).changeBookName(bookDto.getId(), bookDto.getBookName());
        verify(bookService, times(1)).changeBookGenre(bookDto.getId(), bookDto.getGenre().getId());
    }

    @DisplayName("update book")
    @Test
    void shouldUpdateBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/books")
                .flashAttr("bookDto", BOOK_DTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS + BOOK_DTO.getId()));

        verify(bookService, times(0)).createOrUpdate(any());
        verify(bookService, times(1)).changeBookName(BOOK_DTO.getId(), BOOK_DTO.getBookName());
        verify(bookService, times(1)).changeBookGenre(BOOK_DTO.getId(), BOOK_DTO.getGenre().getId());
    }

    @DisplayName("delete book")
    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/{bookId}", BOOK_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS));

        verify(bookService, times(1)).removeById(BOOK_ID);
    }

    @DisplayName("add comment to book")
    @Test
    void shouldAddCommentToBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/{bookId}/comment", BOOK_ID)
                .param("newCommentText", COMMENT_TEXT))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS + BOOK_ID));

        verify(bookService, times(1)).addComment(BOOK_ID, COMMENT_TEXT);
    }

    @DisplayName("delete comment from book")
    @Test
    void shouldDeleteCommentFromBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/{bookId}/comment/{commentId}", BOOK_ID, COMMENT_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS + BOOK_ID));

        verify(bookService, times(1)).removeCommentFromBook(BOOK_ID, COMMENT_ID);
    }

    @DisplayName("add author to book")
    @Test
    void shouldAddAuthorToBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/{bookId}/author", BOOK_ID)
                .param("authorId", AUTHOR_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS + BOOK_ID));

        verify(bookService, times(1)).addBookAuthor(BOOK_ID, AUTHOR_ID);
    }

    @DisplayName("delete author from book")
    @Test
    void shouldDeleteAuthorFromBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/{bookId}/author/{authorId}", BOOK_ID, AUTHOR_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_BOOKS + BOOK_ID));

        verify(bookService, times(1)).removeBookAuthor(BOOK_ID, AUTHOR_ID);
    }

}