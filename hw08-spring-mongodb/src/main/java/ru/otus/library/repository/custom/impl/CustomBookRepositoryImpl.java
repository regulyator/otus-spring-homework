package ru.otus.library.repository.custom.impl;

import com.mongodb.BasicDBObject;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
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
    public Book deleteBookComment(String idBook, String idComment) {
        Update update = new Update().pull("comments", new BasicDBObject("id", idComment));
        return mongoOperations.findAndModify(Query.query(Criteria.where("id").is(idBook)),
                update,
                FindAndModifyOptions.options().returnNew(true), Book.class);
    }

    @Override
    public Book updateBooksGenre(Genre newGenre) {
        Update updateGenre = new Update().set("genre", newGenre);
        return mongoOperations.findAndModify(Query.query(Criteria.where("genre.id").is(newGenre.getId())),
                updateGenre,
                FindAndModifyOptions.options().returnNew(true),
                Book.class);
    }
}
