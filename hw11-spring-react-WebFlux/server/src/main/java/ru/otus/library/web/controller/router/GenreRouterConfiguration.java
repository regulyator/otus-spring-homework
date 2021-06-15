package ru.otus.library.web.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.GenreRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class GenreRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> createGenreRouter(GenreRepository genreRepository) {
        return RouterFunctions.route()
                .GET("/library/api/genres",
                        accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(genreRepository.findAll(), Genre.class))
                .PUT("/library/api/genres",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Genre.class))
                                .doOnNext(genreRepository::save)
                                .flatMap(genre ->
                                        ok().contentType(APPLICATION_JSON).body(genre, Genre.class)
                                ))
                .POST("/library/api/genres",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Genre.class))
                                .doOnNext(genreRepository::save)
                                .flatMap(genre -> ServerResponse
                                        .created(URI.create("/library/api/genres/" + genre.getId()))
                                        .contentType(APPLICATION_JSON)
                                        .build())
                )
                .DELETE("/library/api/genres/{genreId}",
                        accept(APPLICATION_JSON),
                        request -> genreRepository.deleteById(request.pathVariable("genreId"))
                                .flatMap(unused -> ok().build()))
                .build();
    }
}
