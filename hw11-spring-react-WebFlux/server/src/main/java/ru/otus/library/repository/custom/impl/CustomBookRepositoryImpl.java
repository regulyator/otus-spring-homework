package ru.otus.library.repository.custom.impl;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.custom.CustomBookRepository;

public class CustomBookRepositoryImpl implements CustomBookRepository {
    private final ReactiveMongoOperations reactiveMongoOperations;

    public CustomBookRepositoryImpl(ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoOperations = reactiveMongoOperations;
    }


    @Override
    public Mono<Void> updateBooksGenre(Genre newGenre) {
        Update updateGenre = new Update().set("genre", newGenre);
        return reactiveMongoOperations.updateMulti(Query.query(Criteria.where("genre.id").is(newGenre.getId())),
                updateGenre,
                Book.class).then();
    }
}
