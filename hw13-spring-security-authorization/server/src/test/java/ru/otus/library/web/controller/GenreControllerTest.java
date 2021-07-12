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
import ru.otus.library.domain.Genre;
import ru.otus.library.service.data.GenreService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityTestConfiguration.class)
@DisplayName("GenreController should")
@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {
    private static final String GENRE_ID = "gId";
    private static final String GENRE_CAPTION = "CAPTION";
    private static final Genre GENRE = new Genre(GENRE_ID, GENRE_CAPTION);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenreService genreService;

    @DisplayName("get all genres")
    @Test
    @WithMockUser(username = "user")
    void shouldGetAllGenres() throws Exception {
        when(genreService.getAll()).thenReturn(Collections.singletonList(GENRE));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/library/api/genres"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<Genre> resultGenres = objectMapper.readValue(json, new TypeReference<List<Genre>>() {
        });

        assertThat(resultGenres).isNotNull()
                .hasSize(1)
                .allMatch(genre -> genre.equals(GENRE));

        verify(genreService, times(1)).getAll();
    }


    @DisplayName("update genre")
    @Test
    @WithMockUser(username = "user")
    void shouldUpdateGenre() throws Exception {
        when(genreService.createOrUpdate(GENRE)).thenReturn(GENRE);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/library/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(GENRE)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Genre resultGenre = objectMapper.readValue(json, Genre.class);

        assertThat(resultGenre).isNotNull()
                .isEqualTo(GENRE);

        verify(genreService, times(1)).createOrUpdate(GENRE);
    }

    @DisplayName("create genre")
    @Test
    @WithMockUser(username = "user")
    void shouldCreateGenre() throws Exception {
        when(genreService.createOrUpdate(GENRE)).thenReturn(GENRE);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/library/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(GENRE)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Genre resultGenre = objectMapper.readValue(json, Genre.class);

        assertThat(resultGenre).isNotNull()
                .isEqualTo(GENRE);

        verify(genreService, times(1)).createOrUpdate(GENRE);
    }

    @DisplayName("delete genre")
    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void shouldDeleteGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/library/api/genres/{genreId}", GENRE_ID))
                .andExpect(status().is2xxSuccessful());

        verify(genreService, times(1)).removeById(GENRE_ID);
    }

    @DisplayName("not delete genre without role ADMIN")
    @Test
    @WithMockUser(username = "user")
    void shouldNotDeleteGenreWithoutRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/library/api/genres/{genreId}", GENRE_ID))
                .andExpect(status().isForbidden());

        verify(genreService, times(0)).removeById(GENRE_ID);
    }

}