package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@DisplayName("GenreDaoJpa should ")
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
    @Autowired
    private TestEntityManager testEntityManager;


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
        Collection<Genre> oldExistingGenres = genreDao.findAll();

        Genre genre = new Genre();
        genre.setCaption(NEW_GENRE_CAPTION);
        genreDao.save(genre);

        Collection<Genre> newExistingGenres = genreDao.findAll();

        assertThat(true).isEqualTo(genre.getId() > 0L);
        assertThat(oldExistingGenres).isNotEqualTo(newExistingGenres);
        assertThat(true).isEqualTo(newExistingGenres.contains(genre));
    }

    @DisplayName("return GENRE by ID")
    @Test
    void shouldReturnGenreById() {
        Genre expectedGenre = new Genre(EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
        Genre actualGenre = genreDao.findById(EXIST_ID_GENRE).orElse(null);

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

        Genre storedGenre = genreDao.findById(EXIST_ID_GENRE).orElse(null);
        assertThat(storedGenre).usingRecursiveComparison().isNotEqualTo(expectedGenre);
        Objects.requireNonNull(storedGenre).setCaption(EXPECTED_UPDATED_CAPTION_GENRE);
        Genre storedUpdatedGenre = genreDao.save(storedGenre);

        assertThat(storedUpdatedGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("delete GENRE by ID")
    @Test
    void shouldDeleteGenre() {
        assertThatCode(() -> genreDao.findById(EXIST_NON_RELATED_ID_GENRE))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXIST_NON_RELATED_ID_GENRE);
        testEntityManager.clear();

        assertThat(true).isEqualTo(genreDao.findById(EXIST_NON_RELATED_ID_GENRE).isEmpty());
    }

    @DisplayName("throw PersistenceException when delete GENRE related to books")
    @Test
    void shouldThrowExceptionWhenTryDeleteGenreRelatedToBooks() {
        assertThatCode(() -> genreDao.findById(EXIST_ID_GENRE))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> genreDao.deleteById(EXIST_ID_GENRE))
                .isInstanceOf(PersistenceException.class);

        assertThatCode(() -> genreDao.findById(EXIST_ID_GENRE))
                .doesNotThrowAnyException();
    }

    private List<Genre> getAllExistingGenres() {
        return List.of(
                new Genre(1, "Horror test genre"),
                new Genre(2, "Fantasy test genre"),
                new Genre(3, "Sci-Fi test genre")
        );
    }

}
