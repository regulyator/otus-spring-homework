package ru.otus.library.configuration.router;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import static reactor.core.publisher.Mono.when;

@WebFluxTest
@DisplayName("AuthorRouterConfiguration should configure routes and ")
class AuthorRouterConfigurationTest {
    private static final String AUTHOR_ID = "aId";
    private static final String AUTHOR_FIO = "FIO";
    private static final Author AUTHOR = new Author(AUTHOR_ID, AUTHOR_FIO);
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;

    private WebTestClient client;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setUp() {
        client = WebTestClient.bindToRouterFunction(context).build();
    }

    @DisplayName("return all authors")
    @Test
    void shouldReturnAllAuthors(){

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

}