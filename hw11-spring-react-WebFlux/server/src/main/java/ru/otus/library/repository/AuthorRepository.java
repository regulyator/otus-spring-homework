package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Author;

import java.util.Collection;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Flux<Author> findAllByIdIn(Collection<String> iDs);
}
