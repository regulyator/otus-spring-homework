package ru.otus.library.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.domain.Author;
import ru.otus.library.service.data.AuthorService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("AuthorController should")
@WebMvcTest(controllers = AuthorController.class)
class AuthorControllerTest {
    private static final String AUTHOR_ID = "aId";
    private static final String AUTHOR_FIO = "FIO";
    private static final Author AUTHOR = new Author(AUTHOR_ID, AUTHOR_FIO);
    private static final String REDIRECT_AUTHORS = "redirect:/authors";
    private static final String AUTHORS_VIEW = "Authors";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;

    @DisplayName("get all authors")
    @Test
    void shouldGetAllAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors"))
                .andExpect(status().isOk())
                .andExpect(view().name(AUTHORS_VIEW));

        verify(authorService, times(1)).getAll();
    }

    @DisplayName("delete author")
    @Test
    void shouldDeleteAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors/{authorId}/delete", AUTHOR_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_AUTHORS));

        verify(authorService, times(1)).removeById(AUTHOR_ID);
    }

    @DisplayName("update author")
    @Test
    void shouldUpdateAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/authors/")
                .flashAttr("author", AUTHOR))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_AUTHORS));

        verify(authorService, times(1)).changeAuthorFio(AUTHOR.getId(), AUTHOR.getFio());
    }

    @DisplayName("create author")
    @Test
    void shouldCreateAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/authors/")
                .param("newAuthorFio", AUTHOR_FIO))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_AUTHORS));

        verify(authorService, times(1)).create(AUTHOR_FIO);
    }

}