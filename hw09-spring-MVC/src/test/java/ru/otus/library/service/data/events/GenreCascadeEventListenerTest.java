package ru.otus.library.service.data.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.library.configuration.MongoConfiguration;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.repository.AbstractRepositoryTest;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("GenreCascadeEventListener should ")
@Import({GenreCascadeEventListener.class, MongoConfiguration.class})
class GenreCascadeEventListenerTest extends AbstractRepositoryTest {

    private static final String REFERENCE_GENRE_CAPTION = "Sci-Fi";
    private static final String NON_REFERENCE_GENRE_CAPTION = "Horror";
    private static final String BOOK_NAME = "Blindsight-test";
    private static final String UPDATED_GENRE_CAPTION = "Updated genre caption";
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("not delete Genre if it has references in any Book and throw Exception")
    @Test
    public void shouldNotDeleteGenreIfItHasReferenceToAnyBookAndThrowException() {
        Genre genre = genreRepository.findAll().stream()
                .filter(a -> a.getCaption().equals(REFERENCE_GENRE_CAPTION)).findFirst().orElseThrow(EntityNotFoundException::new);

        assertThatThrownBy(() -> genreRepository.delete(genre))
                .isInstanceOf(ReferenceEntityException.class);

        assertThat(genreRepository.findAll()).
                contains(genre);
    }

    @DisplayName("delete Genre if it has no references in any Book")
    @Test
    public void shouldDeleteGenreIfItHasNoReferenceToAnyBook() {
        Genre genre = genreRepository.findAll().stream()
                .filter(a -> a.getCaption().equals(NON_REFERENCE_GENRE_CAPTION)).findFirst().orElseThrow(EntityNotFoundException::new);

        assertThatCode(() -> genreRepository.delete(genre))
                .doesNotThrowAnyException();

        assertThat(genreRepository.findAll())
                .doesNotContain(genre);
    }

    @DisplayName("update reference Genre caption in Book when Genre is updated")
    @Test
    public void shouldUpdateGenreCaptionInReferenceBook() {
        Genre genre = genreRepository.findAll().stream()
                .filter(a -> a.getCaption().equals(REFERENCE_GENRE_CAPTION)).findFirst().orElseThrow(EntityNotFoundException::new);
        genre.setCaption(UPDATED_GENRE_CAPTION);
        genreRepository.save(genre);

        List<Book> books = bookRepository.findAll();


        assertThat(books).isNotEmpty()
                .noneMatch(book -> book.getGenre().getCaption().equals(REFERENCE_GENRE_CAPTION));

    }

}