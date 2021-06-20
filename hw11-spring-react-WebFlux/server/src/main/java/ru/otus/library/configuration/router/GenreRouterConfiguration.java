package ru.otus.library.configuration.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class GenreRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> createGenreRouter(GenreRepository genreRepository,
                                                            BookRepository bookRepository) {
        return RouterFunctions.route()
                .GET("/library/api/genres",
                        accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(genreRepository.findAll(), Genre.class))
                .PUT("/library/api/genres",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Genre.class))
                                .flatMap(genre -> genreRepository.save(genre)
                                        .doOnNext(bookRepository::updateBooksGenre)
                                        .flatMap(genre1 ->
                                                ok().contentType(APPLICATION_JSON)
                                                        .body(Mono.just(genre1), Genre.class)
                                        ))
                )
                .POST("/library/api/genres",
                        accept(APPLICATION_JSON),
                        request -> request.body(toMono(Genre.class))
                                .flatMap(genre -> ServerResponse
                                        .created(URI.create("/library/api/genres/" + genre.getId()))
                                        .contentType(APPLICATION_JSON)
                                        .body(genreRepository.save(genre), Genre.class))
                )
                .DELETE("/library/api/genres/{genreId}",
                        accept(APPLICATION_JSON),
                        request ->
                                bookRepository.existsBookByGenre_Id(request.pathVariable("genreId"))
                                        .flatMap(isReferenceToBook -> {
                                            if (isReferenceToBook) {
                                                return badRequest().body(Mono.just("Reference delete error! First remove this genre from Books!"), String.class);
                                            } else {
                                                return genreRepository.deleteById(request.pathVariable("genreId"))
                                                        .flatMap(unused -> ok().build());
                                            }
                                        }))
                .build();
    }
}
