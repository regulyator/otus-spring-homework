package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

@DisplayName("GenreDaoJdbc should ")
@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {

    private static final long EXIST_ID_GENRE = 2;
    private static final long EXIST_NON_RELATED_ID_GENRE = 1;
    private static final long NON_EXIST_ID_GENRE = 4;
    private static final long EMPTY_ID_GENRE = 0L;
    private static final String EXIST_CAPTION_GENRE = "Fantasy test genre";
    private static final String EXPECTED_UPDATED_CAPTION_GENRE = "Fantasy test genre UPDATED";
    private static final String NEW_GENRE_CAPTION = "New genre";
    @Autowired
    private GenreDao genreDao;


    @DisplayName("return true if GENRE id exist")
    @Test
    void shouldReturnTrueIfGenreIdExist() {
        assertThat(true).isEqualTo(genreDao.isExistById(EXIST_ID_GENRE));
    }

    @DisplayName("return false if GENRE id not exist")
    @Test
    void shouldReturnTrueIfGenreIdNotExist() {
        assertThat(false).isEqualTo(genreDao.isExistById(NON_EXIST_ID_GENRE));
    }

    @DisplayName("insert GENRE with generated ID")
    @Test
    void shouldInsertGenreAndGenerateId() {
        Genre expectedGenre = new Genre(EMPTY_ID_GENRE, NEW_GENRE_CAPTION);
        long generatedId = genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.findById(generatedId);

        assertThat(generatedId).isNotEqualTo(expectedGenre.getId());
        assertThat(actualGenre.getCaption()).isEqualTo(expectedGenre.getCaption());
    }

    @DisplayName("return GENRE by ID")
    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = new Genre(EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
        Genre actualGenre = genreDao.findById(EXIST_ID_GENRE);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("return all GENRE")
    @Test
    void shouldReturnAllGenre() {
        Collection<Genre> expectedGenres = getAllExistingGenres();
        Collection<Genre> actualGenres = genreDao.findAll();

        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @DisplayName("update GENRE")
    @Test
    void shouldUpdateGenre() {
        Genre expectedGenre = new Genre(EXIST_ID_GENRE, EXPECTED_UPDATED_CAPTION_GENRE);

        Genre storedGenre = genreDao.findById(EXIST_ID_GENRE);
        assertThat(storedGenre).usingRecursiveComparison().isNotEqualTo(expectedGenre);
        storedGenre.setCaption(EXPECTED_UPDATED_CAPTION_GENRE);
        genreDao.update(storedGenre);
        Genre storedUpdatedGenre = genreDao.findById(EXIST_ID_GENRE);

        assertThat(storedUpdatedGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("delete GENRE by ID")
    @Test
    void shouldDeleteGenre() {
        assertThatCode(() -> genreDao.findById(EXIST_NON_RELATED_ID_GENRE))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXIST_NON_RELATED_ID_GENRE);

        assertThatThrownBy(() -> genreDao.findById(EXIST_NON_RELATED_ID_GENRE))
                .isInstanceOf(EmptyResultDataAccessException.class);
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
    }

    @DisplayName("throw DaoInsertNonEmptyIdException when insert GENRE with non 0L ID")
    @Test
    void shouldThrowExceptionWhenTryInsertWithNonEmptyId() {
        Genre insertedGenre = new Genre(NON_EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
        Collection<Genre> expectedGenres = getAllExistingGenres();

        assertThatThrownBy(() -> genreDao.insert(insertedGenre))
                .isInstanceOf(DaoInsertNonEmptyIdException.class);
        Collection<Genre> actualGenres = genreDao.findAll();

        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @DisplayName("throw DaoUpdateEmptyIdException when update GENRE with 0L ID")
    @Test
    void shouldThrowExceptionWhenTryUpdateWithEmptyId() {
        Genre updatedGenre = new Genre(EMPTY_ID_GENRE, EXPECTED_UPDATED_CAPTION_GENRE);
        Collection<Genre> expectedGenres = getAllExistingGenres();

        assertThatThrownBy(() -> genreDao.update(updatedGenre))
                .isInstanceOf(DaoUpdateEmptyIdException.class);
        Collection<Genre> actualGenres = genreDao.findAll();

        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    private List<Genre> getAllExistingGenres() {
        return List.of(
                new Genre(1, "Horror test genre"),
                new Genre(2, "Fantasy test genre"),
                new Genre(3, "Sci-Fi test genre")
        );
    }

}
