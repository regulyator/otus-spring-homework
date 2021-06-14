package ru.otus.library.web.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AuthorRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> createAuthorRouter(AuthorRepository authorRepository) {
        return RouterFunctions.route()
                .GET("/library/api/authors",
                        accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(authorRepository.findAll(), Author.class))
                .PUT("/library/api/authors",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Author.class))
                                .doOnNext(authorRepository::save)
                                .flatMap(author ->
                                        ok().contentType(APPLICATION_JSON).body(author, Author.class)
                                ))
                .POST("/library/api/authors",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Author.class))
                                .doOnNext(authorRepository::save)
                                .flatMap(author -> ServerResponse
                                        .created(URI.create("/library/api/authors/" + author.getId()))
                                        .contentType(APPLICATION_JSON)
                                        .build())
                )
                .DELETE("/library/api/authors/{authorId}",
                        accept(APPLICATION_JSON),
                        request -> authorRepository.deleteById(request.pathVariable("authorId"))
                                .flatMap(unused -> ok().build()))
                .build();
    }
}
