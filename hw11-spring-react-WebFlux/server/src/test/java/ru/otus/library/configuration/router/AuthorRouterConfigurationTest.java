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
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import static org.mockito.Mockito.*;

@WebFluxTest
@ContextConfiguration(classes = {AuthorRouterConfiguration.class})
@DisplayName("AuthorRouterConfiguration should configure routes and ")
class AuthorRouterConfigurationTest {
    private static final String AUTHOR_ID = "aId";
    private static final String AUTHOR_FIO = "FIO";
    private static final Author AUTHOR = new Author(AUTHOR_ID, AUTHOR_FIO);

    @Autowired
    @Qualifier("createAuthorRouter")
    private RouterFunction<ServerResponse> authorRouter;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    private WebTestClient client;


    @DisplayName("return all authors")
    @Test
    void shouldReturnAllAuthors() {
        client = WebTestClient.bindToRouterFunction(authorRouter).build();

        doReturn(Flux.just(AUTHOR)).when(authorRepository).findAll();

        this.client
                .get()
                .uri("/library/api/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(AUTHOR.getId())
                .jsonPath("$.[0].fio").isEqualTo(AUTHOR.getFio());
    }

    @DisplayName("update author")
    @Test
    void shouldUpdateAuthor() {
        client = WebTestClient.bindToRouterFunction(authorRouter).build();

        doReturn(Mono.just(AUTHOR)).when(authorRepository).save(AUTHOR);
        this.client
                .put()
                .uri("/library/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(AUTHOR), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(AUTHOR.getId())
                .jsonPath("$.fio").isEqualTo(AUTHOR.getFio());
        ;

        verify(authorRepository, times(1)).save(AUTHOR);
    }

    @DisplayName("create new author")
    @Test
    void shouldCreateNewAuthor() {
        client = WebTestClient.bindToRouterFunction(authorRouter).build();

        doReturn(Mono.just(AUTHOR)).when(authorRepository).save(AUTHOR);
        this.client
                .post()
                .uri("/library/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(AUTHOR), Author.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("/library/api/authors/" + AUTHOR_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(AUTHOR.getId())
                .jsonPath("$.fio").isEqualTo(AUTHOR.getFio());
        ;

        verify(authorRepository, times(1)).save(AUTHOR);
    }

    @DisplayName("delete author")
    @Test
    void shouldDeleteAuthor() {
        client = WebTestClient.bindToRouterFunction(authorRouter).build();

        doReturn(Mono.empty()).when(authorRepository).deleteById(AUTHOR_ID);
        doReturn(Mono.just(false)).when(bookRepository).existsBookByAuthors_Id(AUTHOR_ID);

        this.client
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/library/api/authors/{authorId}").build(AUTHOR_ID))
                .exchange()
                .expectStatus().isOk();

        verify(authorRepository, times(1)).deleteById(AUTHOR_ID);
        verify(bookRepository, times(1)).existsBookByAuthors_Id(AUTHOR_ID);
    }

    @DisplayName("not delete author if it has reference book")
    @Test
    void shouldNotDeleteAuthorIfItHasReferenceBook() {
        client = WebTestClient.bindToRouterFunction(authorRouter).build();

        doReturn(Mono.empty()).when(authorRepository).deleteById(AUTHOR_ID);
        doReturn(Mono.just(true)).when(bookRepository).existsBookByAuthors_Id(AUTHOR_ID);

        this.client
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/library/api/authors/{authorId}").build(AUTHOR_ID))
                .exchange()
                .expectStatus().isBadRequest();

        verify(authorRepository, times(0)).deleteById(AUTHOR_ID);
        verify(bookRepository, times(1)).existsBookByAuthors_Id(AUTHOR_ID);
    }

}