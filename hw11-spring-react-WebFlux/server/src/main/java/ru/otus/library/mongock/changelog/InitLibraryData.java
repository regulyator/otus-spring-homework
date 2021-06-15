package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@ChangeLog
public class InitLibraryData {

    private static final String BOOKS_COLLECTION_NAME = "Books";
    private static final String AUTHORS_COLLECTION_NAME = "Authors";
    private static final String GENRES_COLLECTION_NAME = "Genres";
    private final ReactiveMongoOperations reactiveMongoOperations;

    public InitLibraryData(ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoOperations = reactiveMongoOperations;
    }

    public void clearDb() {
        reactiveMongoOperations.dropCollection(BOOKS_COLLECTION_NAME);
        reactiveMongoOperations.dropCollection(AUTHORS_COLLECTION_NAME);
        reactiveMongoOperations.dropCollection(GENRES_COLLECTION_NAME);
    }

    public void initAuthors() {
        Stream.of("Peter Watts",
                "Robert Hainline",
                "Arkady and Boris Strugatsky",
                "Vernor Vinge").forEach(s -> reactiveMongoOperations.save(new Author(null, s)));
    }


    public void initGenres() {
        Stream.of("Horror",
                "Fantasy",
                "Sci-Fi").forEach(s -> reactiveMongoOperations.save(new Genre(null, s)));
    }


    public void initBooks() {

        Book book1 = new Book(null, "Blindsight",
                null,
                Collections.emptyList(),
                List.of(new Comment("nice book!"), new Comment("So complicated")));

        reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class)
                .doOnNext(book1::setGenre)
                .and(reactiveMongoOperations.find(Query.query(Criteria.where("fio").is("Peter Watts")), Author.class))


        reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class)
                .and(reactiveMongoOperations.find(Query.query(Criteria.where("fio").is("Peter Watts")), Author.class))


        reactiveMongoOperations.save(new Book(null, "Blindsight",
                reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                mongoOperations.find(Query.query(Criteria.where("fio").is("Peter Watts")), Author.class),
                List.of(new Comment("nice book!"), new Comment("So complicated"))));

        bookRepository.save(new Book(null, "The Moon Is a Harsh Mistress",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                mongoOperations.find(Query.query(Criteria.where("fio").is("Robert Hainline")), Author.class),
                List.of(new Comment("atata"), new Comment("Mooooooon!"))));

        bookRepository.save(new Book(null, "Prisoners of Power",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Fantasy")), Genre.class),
                mongoOperations.find(Query.query(Criteria.where("fio").is("Arkady and Boris Strugatsky")), Author.class),
                List.of(new Comment("What a brilliant book!"), new Comment("ohhhhh"))));

        bookRepository.save(new Book(null, "Prisoners of Power, Blindsight",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                mongoOperations.find(Query.query(Criteria.where("fio").in("Peter Watts", "Arkady and Boris Strugatsky")), Author.class),
                List.of(new Comment("ohhhhh"))));

        bookRepository.save(new Book(null, "The Moon Is a Harsh Mistress, Prisoners of Power",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                mongoOperations.find(Query.query(Criteria.where("fio").in("Robert Hainline", "Arkady and Boris Strugatsky")), Author.class),
                null));
    }
}
