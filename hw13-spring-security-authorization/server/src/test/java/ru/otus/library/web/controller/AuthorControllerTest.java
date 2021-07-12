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
import ru.otus.library.domain.Author;
import ru.otus.library.service.data.AuthorService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityTestConfiguration.class)
@DisplayName("AuthorController should")
@WebMvcTest(controllers = AuthorController.class)
class AuthorControllerTest {
    private static final String AUTHOR_ID = "aId";
    private static final String AUTHOR_FIO = "FIO";
    private static final Author AUTHOR = new Author(AUTHOR_ID, AUTHOR_FIO);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;

    @DisplayName("get all authors")
    @Test
    @WithMockUser(username = "user")
    void shouldGetAllAuthors() throws Exception {
        when(authorService.getAll()).thenReturn(Collections.singletonList(AUTHOR));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/library/api/authors"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<Author> resultAuthors = objectMapper.readValue(json, new TypeReference<List<Author>>() {
        });

        assertThat(resultAuthors).isNotNull()
                .hasSize(1)
                .allMatch(author -> author.equals(AUTHOR));

        verify(authorService, times(1)).getAll();
    }

    @DisplayName("update author")
    @Test
    @WithMockUser(username = "user")
    void shouldUpdateAuthor() throws Exception {
        when(authorService.createOrUpdate(AUTHOR)).thenReturn(AUTHOR);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/library/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AUTHOR)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Author resultAuthor = objectMapper.readValue(json, Author.class);

        assertThat(resultAuthor).isNotNull()
                .isEqualTo(AUTHOR);

        verify(authorService, times(1)).createOrUpdate(AUTHOR);
    }

    @DisplayName("create author")
    @Test
    @WithMockUser(username = "user")
    void shouldCreateAuthor() throws Exception {
        when(authorService.createOrUpdate(AUTHOR)).thenReturn(AUTHOR);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/library/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AUTHOR)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Author resultAuthor = objectMapper.readValue(json, Author.class);

        assertThat(resultAuthor).isNotNull()
                .isEqualTo(AUTHOR);

        verify(authorService, times(1)).createOrUpdate(AUTHOR);
    }


    @DisplayName("delete author")
    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void shouldDeleteAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/library/api/authors/{authorId}", AUTHOR_ID))
                .andExpect(status().is2xxSuccessful());

        verify(authorService, times(1)).removeById(AUTHOR_ID);
    }

    @DisplayName("not delete author without role ADMIN")
    @Test
    @WithMockUser(username = "user")
    void shouldNotDeleteAuthorWithoutRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/library/api/authors/{authorId}", AUTHOR_ID))
                .andExpect(status().isForbidden());

        verify(authorService, times(0)).removeById(AUTHOR_ID);
    }


}