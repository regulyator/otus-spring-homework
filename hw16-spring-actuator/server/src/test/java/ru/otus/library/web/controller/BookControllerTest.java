package ru.otus.library.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.config.SecurityTestConfiguration;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.service.data.BookService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityTestConfiguration.class)
@DisplayName("BookController should")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
    private static final String BOOK_ID = "bId";
    private static final Genre GENRE = new Genre("GENRE_ID", "GENRE_CAPTION");
    private static final Book BOOK = new Book(BOOK_ID,
            "BOOK_NAME",
            GENRE,
            Collections.emptyList(),
            Collections.emptyList());
    private static final BookDto BOOK_DTO = new BookDto(BOOK);
    private static final String NEW_COMMENT_TEXT = "new comment";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;


    @DisplayName("get all books")
    @Test
    @WithMockUser(username = "user")
    void shouldGetAllBooks() throws Exception {
        when(bookService.getAllDto()).thenReturn(Collections.singletonList(BOOK_DTO));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/library/api/books"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<BookDto> resultBooks = objectMapper.readValue(json, new TypeReference<List<BookDto>>() {
        });

        assertThat(resultBooks).isNotNull()
                .hasSize(1)
                .allMatch(bookDto -> bookDto.equals(BOOK_DTO));

        verify(bookService, times(1)).getAllDto();
    }


    @DisplayName("get book by id")
    @Test
    @WithMockUser(username = "user")
    void shouldGetBookById() throws Exception {
        when(bookService.getByIdDto(BOOK_ID)).thenReturn(BOOK_DTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/library/api/books/{bookId}", BOOK_ID))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        BookDto resultBook = objectMapper.readValue(json, BookDto.class);

        assertThat(resultBook).isNotNull()
                .isEqualTo(BOOK_DTO);

        verify(bookService, times(1)).getByIdDto(BOOK_ID);
    }

    @DisplayName("update book")
    @Test
    @WithMockUser(username = "user")
    void shouldUpdateBook() throws Exception {
        when(bookService.createOrUpdate(BOOK_DTO)).thenReturn(BOOK_DTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/library/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BOOK_DTO)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        BookDto resultBook = objectMapper.readValue(json, BookDto.class);

        assertThat(resultBook).isNotNull()
                .isEqualTo(BOOK_DTO);

        verify(bookService, times(1)).createOrUpdate(BOOK_DTO);
    }

    @DisplayName("create book")
    @Test
    @WithMockUser(username = "user")
    void shouldCreateBook() throws Exception {
        when(bookService.createOrUpdate(BOOK_DTO)).thenReturn(BOOK_DTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/library/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BOOK_DTO)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        BookDto resultBook = objectMapper.readValue(json, BookDto.class);

        assertThat(resultBook).isNotNull()
                .isEqualTo(BOOK_DTO);

        verify(bookService, times(1)).createOrUpdate(BOOK_DTO);
    }

    @DisplayName("add comment to book")
    @Test
    @WithMockUser(username = "user")
    void shouldAddCommentToBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/library/api/books/{bookId}/comment", BOOK_ID)
                .param("newCommentText", NEW_COMMENT_TEXT))
                .andExpect(status().is2xxSuccessful());

        verify(bookService, times(1)).addComment(BOOK_ID, NEW_COMMENT_TEXT);
    }

    @DisplayName("delete book")
    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/library/api/books/{bookId}", BOOK_ID))
                .andExpect(status().is2xxSuccessful());

        verify(bookService, times(1)).removeById(BOOK_ID);
    }

    @DisplayName("not delete book without role ADMIN")
    @Test
    @WithMockUser(username = "user")
    void shouldNotDeleteBookWithoutRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/library/api/books/{bookId}", BOOK_ID))
                .andExpect(status().isForbidden());

        verify(bookService, times(0)).removeById(BOOK_ID);
    }

}