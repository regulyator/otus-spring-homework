package ru.otus.library.repository.custom;

import reactor.core.publisher.Mono;
import ru.otus.library.domain.Genre;

public interface CustomBookRepository {

    Mono<Void> updateBooksGenre(Genre newGenre);
}
