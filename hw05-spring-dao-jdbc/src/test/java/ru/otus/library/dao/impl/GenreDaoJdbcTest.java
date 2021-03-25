package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("GenreDaoJdbc should ")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    public static final long EXIST_ID_GENRE = 2;
    public static final long EXIST_NON_RELATED_ID_GENRE = 4;
    public static final long NON_EXIST_ID_GENRE = 6;
    public static final long EMPTY_ID_GENRE = 0L;
    public static final String EXIST_CAPTION_GENRE = "FANTASY TEST GENRE";
    public static final String EXPECTED_UPDATED_CAPTION_GENRE = "FANTASY TEST GENRE UPDATED";
    @Autowired
    @SpyBean
    private GenreDao genreDao;

    @DisplayName("insert GENRE with generated ID")
    @Test
    void shouldInsertGenreAndGenerateId() {
        Genre expectedGenre = new Genre(0L, "NEW GENRE TEST");
        long generatedId = genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.findById(generatedId);

        assertThat(generatedId).isNotEqualTo(expectedGenre.getId());
        assertThat(actualGenre.getCaption()).isEqualTo(expectedGenre.getCaption());
        verify(genreDao, times(1)).insert(expectedGenre);
    }

    @DisplayName("return GENRE by ID")
    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = new Genre(EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
        Genre actualGenre = genreDao.findById(EXIST_ID_GENRE);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
        verify(genreDao, times(1)).findById(EXIST_ID_GENRE);
    }

    @DisplayName("return all GENRE")
    @Test
    void shouldReturnAllGenre() {
        Collection<Genre> expectedGenres = getAllExistingGenres();
        Collection<Genre> actualGenres = genreDao.findAll();

        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
        verify(genreDao, times(1)).findAll();
    }

    @DisplayName("update GENRE")
    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre(EXIST_ID_GENRE, EXPECTED_UPDATED_CAPTION_GENRE);

        Genre storedGenre = genreDao.findById(EXIST_ID_GENRE);
        storedGenre.setCaption(EXPECTED_UPDATED_CAPTION_GENRE);
        genreDao.update(storedGenre);
        Genre storedUpdatedGenre = genreDao.findById(EXIST_ID_GENRE);

        assertThat(storedUpdatedGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
        verify(genreDao, times(0)).insert(any());
        verify(genreDao, times(1)).update(expectedGenre);
    }

    @DisplayName("delete GENRE by ID")
    @Test
    void shouldDeleteGenre() {
        assertThatCode(() -> genreDao.findById(EXIST_NON_RELATED_ID_GENRE))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXIST_NON_RELATED_ID_GENRE);

        assertThatThrownBy(() -> genreDao.findById(EXIST_NON_RELATED_ID_GENRE))
                .isInstanceOf(EmptyResultDataAccessException.class);
        verify(genreDao, times(1)).deleteById(EXIST_NON_RELATED_ID_GENRE);
    }

    @DisplayName("throw DataIntegrityViolationException when delete GENRE related to books")
    @Test
    void shouldThrowExceptionWhenTryDeleteGenreRelatedToBooks() {
        assertThatCode(() -> genreDao.findById(EXIST_ID_GENRE))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> genreDao.deleteById(EXIST_ID_GENRE))
                .isInstanceOf(DataIntegrityViolationException.class);

        assertThatCode(() -> genreDao.findById(EXIST_ID_GENRE))
                .doesNotThrowAnyException();

        verify(genreDao, times(1)).deleteById(EXIST_ID_GENRE);
    }

    @DisplayName("throw DaoInsertNonEmptyIdException when insert GENRE with non 0L ID")
    @Test
    void shouldThrowExceptionWhenTryInsertWithNonEmptyId() {
        Genre insertedGenre = new Genre(NON_EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
        Collection<Genre> expectedGenres = getAllExistingGenres();
        Collection<Genre> actualGenres = genreDao.findAll();

        assertThatThrownBy(() -> genreDao.insert(insertedGenre))
                .isInstanceOf(DaoInsertNonEmptyIdException.class);

        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @DisplayName("throw DaoUpdateEmptyIdException when update GENRE with 0L ID")
    @Test
    void shouldThrowExceptionWhenTryUpdateWithEmptyId() {
        Genre updatedGenre = new Genre(EMPTY_ID_GENRE, EXPECTED_UPDATED_CAPTION_GENRE);
        Collection<Genre> expectedGenres = getAllExistingGenres();
        Collection<Genre> actualGenres = genreDao.findAll();

        assertThatThrownBy(() -> genreDao.update(updatedGenre))
                .isInstanceOf(DaoUpdateEmptyIdException.class);

        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    private List<Genre> getAllExistingGenres() {
        return List.of(
                new Genre(1, "HORROR TEST GENRE"),
                new Genre(2, "FANTASY TEST GENRE"),
                new Genre(3, "SKY-FI TEST GENRE")
        );
    }

}
