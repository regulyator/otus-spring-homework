package ru.otus.library.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.data.GenreService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("GenreController should")
@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {
    private static final String GENRE_ID = "gId";
    private static final String GENRE_CAPTION = "CAPTION";
    private static final Genre GENRE = new Genre(GENRE_ID, GENRE_CAPTION);
    private static final String REDIRECT_GENRES = "redirect:/genres";
    private static final String GENRES_VIEW = "Genres";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenreService genreService;

    @DisplayName("get all genres")
    @Test
    void shouldGetAllGenres() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/genres"))
                .andExpect(status().isOk())
                .andExpect(view().name(GENRES_VIEW));

        verify(genreService, times(1)).getAll();
    }

    @DisplayName("delete genre")
    @Test
    void shouldDeleteGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/genres/{genreId}/delete", GENRE_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_GENRES));

        verify(genreService, times(1)).removeById(GENRE_ID);
    }

    @DisplayName("update genre")
    @Test
    void shouldUpdateGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/genres/")
                .flashAttr("genre", GENRE))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_GENRES));

        verify(genreService, times(1)).changeGenreCaption(GENRE.getId(), GENRE.getCaption());
    }

    @DisplayName("create genre")
    @Test
    void shouldCreateGenre() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/genres/")
                .param("newGenreCaption", GENRE_CAPTION))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_GENRES));

        verify(genreService, times(1)).create(GENRE_CAPTION);
    }

}