package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.List;
import java.util.stream.Stream;

@ChangeLog
public class InitLibraryData {

    @ChangeSet(order = "001", id = "dropDb", author = "regulyator", runAlways = true)
    public void dropDb(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "regulyator", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        Stream.of("Peter Watts",
                "Robert Hainline",
                "Arkady and Boris Strugatsky",
                "Vernor Vinge").forEach(s -> authorRepository.save(new Author(null, s)));
    }

    @ChangeSet(order = "003", id = "initGenres", author = "regulyator", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        Stream.of("Horror",
                "Fantasy",
                "Sci-Fi").forEach(s -> genreRepository.save(new Genre(null, s)));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "regulyator", runAlways = true)
    public void initBooks(BookRepository bookRepository, MongoOperations mongoOperations) {
        bookRepository.save(new Book(null, "Blindsight",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
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
