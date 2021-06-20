package ru.otus.library.configuration.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AuthorRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> createAuthorRouter(AuthorRepository authorRepository,
                                                             BookRepository bookRepository) {
        return RouterFunctions.route()
                .GET("/library/api/authors",
                        accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(authorRepository.findAll(), Author.class))
                .PUT("/library/api/authors",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Author.class))
                                .flatMap(author ->
                                        ok().contentType(APPLICATION_JSON)
                                                .body(authorRepository.save(author), Author.class)
                                ))
                .POST("/library/api/authors",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Author.class))
                                .flatMap(author -> ServerResponse
                                        .created(URI.create("/library/api/authors/" + author.getId()))
                                        .contentType(APPLICATION_JSON)
                                        .body(authorRepository.save(author), Author.class))
                )
                .DELETE("/library/api/authors/{authorId}",
                        accept(APPLICATION_JSON),
                        request ->
                                bookRepository.existsBookByAuthors_Id(request.pathVariable("authorId"))
                                        .flatMap(isReferenceToBook -> {
                                            if (isReferenceToBook) {
                                                return badRequest().body(Mono.just("Reference delete error! First remove this author from Books!"), String.class);
                                            } else {
                                                return authorRepository.deleteById(request.pathVariable("authorId"))
                                                        .flatMap(unused -> ok().build());
                                            }
                                        }))
                .build();
    }
}
