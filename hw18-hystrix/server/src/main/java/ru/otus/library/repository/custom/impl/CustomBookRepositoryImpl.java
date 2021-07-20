package ru.otus.library.repository.custom.impl;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.custom.CustomBookRepository;

public class CustomBookRepositoryImpl implements CustomBookRepository {
    private final MongoOperations mongoOperations;

    public CustomBookRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void updateBooksGenre(Genre newGenre) {
        Update updateGenre = new Update().set("genre", newGenre);
        mongoOperations.updateMulti(Query.query(Criteria.where("genre.id").is(newGenre.getId())),
                updateGenre,
                Book.class);
    }
}
