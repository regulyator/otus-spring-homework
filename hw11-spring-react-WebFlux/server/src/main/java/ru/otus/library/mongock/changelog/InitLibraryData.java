package ru.otus.library.mongock.changelog;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static reactor.core.publisher.Flux.just;

@Service
public class InitLibraryData {

    private static final String BOOKS_COLLECTION_NAME = "Books";
    private static final String AUTHORS_COLLECTION_NAME = "Authors";
    private static final String GENRES_COLLECTION_NAME = "Genres";
    private final ReactiveMongoOperations reactiveMongoOperations;

    public InitLibraryData(ReactiveMongoOperations reactiveMongoOperations) {
        this.reactiveMongoOperations = reactiveMongoOperations;
        initDb();
    }

    public void initDb() {
        Book book1 = Book.builder()
                .bookName("Blindsight 1111")
                .comments(List.of(new Comment("nice book!"), new Comment("So complicated")))
                .authors(new ArrayList<>())
                .build();

        Book book2 = Book.builder()
                .bookName("The Moon Is a Harsh Mistress")
                .comments(List.of(new Comment("atata"), new Comment("Mooooooon!")))
                .authors(new ArrayList<>())
                .build();

        Book book3 = Book.builder()
                .bookName("Prisoners of Power")
                .comments(List.of(new Comment("What a brilliant book!"), new Comment("ohhhhh")))
                .authors(new ArrayList<>())
                .build();

        Book book4 = Book.builder()
                .bookName("Prisoners of Power, Blindsight")
                .comments(List.of(new Comment("ohhhhh")))
                .authors(new ArrayList<>())
                .build();

        Book book5 = Book.builder()
                .bookName("The Moon Is a Harsh Mistress, Prisoners of Power")
                .authors(new ArrayList<>())
                .build();

        reactiveMongoOperations.dropCollection(BOOKS_COLLECTION_NAME)
                .then(reactiveMongoOperations.dropCollection(AUTHORS_COLLECTION_NAME))
                .then(reactiveMongoOperations.dropCollection(GENRES_COLLECTION_NAME))
                .then(just("Peter Watts",
                        "Robert Hainline",
                        "Arkady and Boris Strugatsky",
                        "Vernor Vinge").doOnNext(s -> reactiveMongoOperations.save(new Author(null, s)))
                        .thenMany(just("Horror",
                                "Fantasy",
                                "Sci-Fi",
                                "Atata")
                                .doOnNext(s -> reactiveMongoOperations.save(new Genre(null, s))))
                        .then(reactiveMongoOperations.find(Query.query(Criteria.where("fio").is("Peter Watts")), Author.class)
                                .doOnEach(author -> book1.getAuthors().add(author.get()))
                                .then(reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class))
                                .doOnNext(book1::setGenre)
                                .then(reactiveMongoOperations.save(book1)))
                        .then(reactiveMongoOperations.find(Query.query(Criteria.where("fio").is("Robert Hainline")), Author.class)
                                .doOnNext(author -> book2.getAuthors().add(author))
                                .then(reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class))
                                .doOnNext(book2::setGenre)
                                .then(reactiveMongoOperations.save(book2)))
                        .then(reactiveMongoOperations.find(Query.query(Criteria.where("fio").is("Arkady and Boris Strugatsky")), Author.class)
                                .doOnNext(author -> book3.getAuthors().add(author))
                                .then(reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Fantasy")), Genre.class))
                                .doOnNext(book3::setGenre)
                                .then(reactiveMongoOperations.save(book3)))
                        .then(reactiveMongoOperations.find(Query.query(Criteria.where("fio").in("Peter Watts", "Arkady and Boris Strugatsky")), Author.class)
                                .doOnNext(author -> book4.getAuthors().add(author))
                                .then(reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class))
                                .doOnNext(book4::setGenre)
                                .then(reactiveMongoOperations.save(book4)))
                        .then(reactiveMongoOperations.find(Query.query(Criteria.where("fio").in("Robert Hainline", "Arkady and Boris Strugatsky")), Author.class)
                                .doOnNext(author -> book5.getAuthors().add(author))
                                .then(reactiveMongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class))
                                .doOnNext(book5::setGenre)
                                .then(reactiveMongoOperations.save(book5)))).subscribe();
    }


}
