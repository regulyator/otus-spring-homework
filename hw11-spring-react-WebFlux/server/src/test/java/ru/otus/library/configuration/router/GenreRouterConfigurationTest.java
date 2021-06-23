package ru.otus.library.configuration.router;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import static org.mockito.Mockito.*;

@WebFluxTest
@ContextConfiguration(classes = {GenreRouterConfiguration.class})
@DisplayName("GenreRouterConfiguration should configure routes and ")
class GenreRouterConfigurationTest {
    private static final String GENRE_ID = "gId";
    private static final String GENRE_CAPTION = "CAPTION";
    private static final Genre GENRE = new Genre(GENRE_ID, GENRE_CAPTION);

    @Autowired
    @Qualifier("createGenreRouter")
    private RouterFunction<ServerResponse> genreRouter;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    private WebTestClient client;

    @DisplayName("return all genres")
    @Test
    void shouldReturnAllGenres() {
        client = WebTestClient.bindToRouterFunction(genreRouter).build();

        doReturn(Flux.just(GENRE)).when(genreRepository).findAll();

        this.client
                .get()
                .uri("/library/api/genres")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(GENRE.getId())
                .jsonPath("$.[0].caption").isEqualTo(GENRE.getCaption());
    }

    @DisplayName("update genre")
    @Test
    void shouldUpdateGenre() {
        client = WebTestClient.bindToRouterFunction(genreRouter).build();

        doReturn(Mono.just(GENRE)).when(genreRepository).save(GENRE);
        this.client
                .put()
                .uri("/library/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(GENRE), Genre.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(GENRE.getId())
                .jsonPath("$.caption").isEqualTo(GENRE.getCaption());
        ;

        verify(genreRepository, times(1)).save(GENRE);
        verify(bookRepository, times(1)).updateBooksGenre(GENRE);
    }

    @DisplayName("create new genre")
    @Test
    void shouldCreateNewGenre() {
        client = WebTestClient.bindToRouterFunction(genreRouter).build();

        doReturn(Mono.just(GENRE)).when(genreRepository).save(GENRE);
        this.client
                .post()
                .uri("/library/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(GENRE), Genre.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("/library/api/genres/" + GENRE_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(GENRE.getId())
                .jsonPath("$.caption").isEqualTo(GENRE.getCaption());


        verify(genreRepository, times(1)).save(GENRE);
    }

    @DisplayName("delete genre")
    @Test
    void shouldDeleteGenre() {
        client = WebTestClient.bindToRouterFunction(genreRouter).build();

        doReturn(Mono.empty()).when(genreRepository).deleteById(GENRE_ID);
        doReturn(Mono.just(false)).when(bookRepository).existsBookByGenre_Id(GENRE_ID);

        this.client
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/library/api/genres/{genreId}").build(GENRE_ID))
                .exchange()
                .expectStatus().isOk();

        verify(genreRepository, times(1)).deleteById(GENRE_ID);
        verify(bookRepository, times(1)).existsBookByGenre_Id(GENRE_ID);
    }

    @DisplayName("not delete genre if it has reference book")
    @Test
    void shouldNotDeleteGenreIfItHasReferenceBook() {
        client = WebTestClient.bindToRouterFunction(genreRouter).build();

        doReturn(Mono.empty()).when(genreRepository).deleteById(GENRE_ID);
        doReturn(Mono.just(true)).when(bookRepository).existsBookByGenre_Id(GENRE_ID);

        this.client
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/library/api/genres/{genreId}").build(GENRE_ID))
                .exchange()
                .expectStatus().isBadRequest();

        verify(genreRepository, times(0)).deleteById(GENRE_ID);
        verify(bookRepository, times(1)).existsBookByGenre_Id(GENRE_ID);
    }

}