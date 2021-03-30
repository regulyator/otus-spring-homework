package ru.otus.library.service.data.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.data.GenreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GenreServiceImpl.class)
@DisplayName(value = "GenreServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GenreServiceImplTest {
    private static final String NEW_GENRE = "NEW GENRE";
    private static final long NEW_ID = 12;
    @MockBean
    private GenreDao genreDao;
    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("insert new GENRE by caption and set id")
    void shouldInsertNewAuthorByFIOAndSetId() {
        when(genreDao.insert(any())).thenReturn(NEW_ID);

        Genre newGenre = genreService.create(NEW_GENRE);

        assertThat(NEW_ID).isEqualTo(newGenre.getId());
    }

}