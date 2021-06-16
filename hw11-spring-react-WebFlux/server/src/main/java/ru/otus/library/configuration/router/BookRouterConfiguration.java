package ru.otus.library.configuration.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.repository.BookRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class BookRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> createBookRouter(BookRepository bookRepository) {
        return RouterFunctions.route()
                .GET("/library/api/books",
                        accept(APPLICATION_JSON),
                        request -> bookRepository.findAll()
                                .flatMap(book -> Mono.just(new BookDto(book)))
                                .collectList()
                                .flatMap(bookDtos -> ok().contentType(APPLICATION_JSON)
                                        .body(Mono.just(bookDtos), BookDto.class))
                )
                .GET("/library/api/books/{bookId}",
                        accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("bookId"))
                                .map(BookDto::new)
                                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON)
                                        .body(bookDto, BookDto.class))
                )
                .PUT("/library/api/books",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(BookDto.class))
                                .map(Book::new)
                                .flatMap(bookRepository::save)
                                .flatMap(book ->
                                        ok().contentType(APPLICATION_JSON).body(Mono.just(new BookDto(book)), BookDto.class)
                                ))
                .PUT("/library/api/books/{bookId}/comment",
                        accept(APPLICATION_JSON),
                        request -> Mono.just(request.queryParam("newCommentText"))
                                .flatMap(s -> Mono.just(s.get()))
                                .ofType(String.class)
                                .flatMap(newCommentText -> bookRepository.findById(request.pathVariable("bookId"))
                                        .flatMap(book -> {
                                            if (Objects.isNull(book.getComments())) {
                                                book.setComments(new ArrayList<>());
                                            }
                                            book.getComments().add(new Comment(newCommentText));
                                            return bookRepository.save(book);
                                        }))
                                .flatMap(book -> ok().contentType(APPLICATION_JSON).body(Mono.just(new BookDto(book)), BookDto.class))
                )
                .POST("/library/api/books",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(BookDto.class))
                                .flatMap(bookDto -> ServerResponse
                                        .created(URI.create("/library/api/books/" + bookDto.getId()))
                                        .contentType(APPLICATION_JSON)
                                        .body(Mono.just(new Book(bookDto))
                                                .flatMap(book ->
                                                        bookRepository.save(book)
                                                                .flatMap(createdBook -> Mono.just(new BookDto(createdBook)))), BookDto.class))
                )
                .DELETE("/library/api/books/{bookId}",
                        accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(request.pathVariable("bookId"))
                                .flatMap(unused -> ok().build()))
                .build();
    }
}
