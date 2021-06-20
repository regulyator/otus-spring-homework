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
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.repository.BookRepository;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@WebFluxTest
@ContextConfiguration(classes = {BookRouterConfiguration.class})
@DisplayName("BookRouterConfiguration should configure routes and ")
class BookRouterConfigurationTest {
    private static final String BOOK_ID = "bId";
    private static final Genre GENRE = new Genre("GENRE_ID", "GENRE_CAPTION");
    private static final Book BOOK = new Book(BOOK_ID,
            "BOOK_NAME",
            GENRE,
            new ArrayList<>(),
            new ArrayList<>());
    private static final BookDto BOOK_DTO = new BookDto(BOOK);
    private static final String NEW_COMMENT_TEXT = "new comment";

    @Autowired
    @Qualifier("createBookRouter")
    private RouterFunction<ServerResponse> bookRouter;

    @MockBean
    private BookRepository bookRepository;

    private WebTestClient client;

    @DisplayName("return all books")
    @Test
    void shouldReturnAllBooks() {
        client = WebTestClient.bindToRouterFunction(bookRouter).build();

        doReturn(Flux.just(BOOK)).when(bookRepository).findAll();

        this.client
                .get()
                .uri("/library/api/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(BOOK_DTO.getId())
                .jsonPath("$.[0].bookName").isEqualTo(BOOK_DTO.getBookName())
                .jsonPath("$.[0].genre").isEqualTo(BOOK_DTO.getGenre());
    }

    @DisplayName("return book by Id")
    @Test
    void shouldReturnBookById() {
        client = WebTestClient.bindToRouterFunction(bookRouter).build();

        doReturn(Mono.just(BOOK)).when(bookRepository).findById(BOOK_ID);

        this.client
                .get()
                .uri("/library/api/books/" + BOOK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(BOOK_DTO.getId())
                .jsonPath("$.bookName").isEqualTo(BOOK_DTO.getBookName())
                .jsonPath("$.genre").isEqualTo(BOOK_DTO.getGenre());
    }

    @DisplayName("update book")
    @Test
    void shouldUpdateBook() {
        client = WebTestClient.bindToRouterFunction(bookRouter).build();

        doReturn(Mono.just(BOOK)).when(bookRepository).save(BOOK);


        this.client
                .put()
                .uri("/library/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(BOOK_DTO), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(BOOK_DTO.getId())
                .jsonPath("$.bookName").isEqualTo(BOOK_DTO.getBookName())
                .jsonPath("$.genre").isEqualTo(BOOK_DTO.getGenre());
        ;

        verify(bookRepository, times(1)).save(BOOK);
    }

    @DisplayName("add comment to book")
    @Test
    void shouldAddCommentToBook() {
        client = WebTestClient.bindToRouterFunction(bookRouter).build();

        doReturn(Mono.just(BOOK)).when(bookRepository).findById(BOOK_ID);
        doAnswer(invocation -> Mono.just(invocation.getArguments()[0])).when(bookRepository).save(any());
        this.client
                .put()
                .uri(uriBuilder -> uriBuilder.path("/library/api/books/{bookId}/comment")
                        .queryParam("newCommentText", NEW_COMMENT_TEXT)
                        .build(BOOK_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(BOOK_DTO), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(BOOK_DTO.getId())
                .jsonPath("$.bookName").isEqualTo(BOOK_DTO.getBookName())
                .jsonPath("$.genre").isEqualTo(BOOK_DTO.getGenre())
                .jsonPath("$.comments.[0].caption").isEqualTo(NEW_COMMENT_TEXT);
        ;

        verify(bookRepository, times(1)).save(BOOK);
    }

    @DisplayName("create new book")
    @Test
    void shouldCreateNewBook() {
        client = WebTestClient.bindToRouterFunction(bookRouter).build();

        doReturn(Mono.just(BOOK)).when(bookRepository).save(BOOK);
        this.client
                .post()
                .uri("/library/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(BOOK_DTO), BookDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("/library/api/books/" + BOOK_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(BOOK_DTO.getId())
                .jsonPath("$.bookName").isEqualTo(BOOK_DTO.getBookName())
                .jsonPath("$.genre").isEqualTo(BOOK_DTO.getGenre());


        verify(bookRepository, times(1)).save(BOOK);
    }

    @DisplayName("delete book")
    @Test
    void shouldDeleteBook() {
        client = WebTestClient.bindToRouterFunction(bookRouter).build();

        doReturn(Mono.empty()).when(bookRepository).deleteById(BOOK_ID);

        this.client
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/library/api/books/{bookId}").build(BOOK_ID))
                .exchange()
                .expectStatus().isOk();

        verify(bookRepository, times(1)).deleteById(BOOK_ID);
    }

}