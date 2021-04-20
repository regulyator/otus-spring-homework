package ru.otus.library.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@ChangeLog
public class DBChangelog {

    public static final String AUTHORS_COLLECTION_NAME = "Authors";
    public static final String GENRES_COLLECTION_NAME = "Genres";

    @ChangeSet(order = "001", id = "dropDb", author = "regulyator", runAlways = true)
    public void dropDb(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "regulyator", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository){
        Stream.of("Peter Watts",
                "Robert Hainline",
                "Arkady and Boris Strugatsky",
                "Vernor Vinge").forEach(s -> authorRepository.save(new Author(null, s)));
    }

    @ChangeSet(order = "003", id = "initGenres", author = "regulyator", runAlways = true)
    public void initGenres(GenreRepository genreRepository){
        Stream.of("Horror",
                "Fantasy",
                "Sci-Fi").forEach(s -> genreRepository.save(new Genre(null, s)));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "regulyator", runAlways = true)
    public void initBooks(BookRepository bookRepository, MongoOperations mongoOperations){
        bookRepository.save(new Book(null, "Blindsight",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                new HashSet<>(mongoOperations.find(Query.query(Criteria.where("fio").is("Peter Watts")), Author.class))));

        bookRepository.save(new Book(null, "The Moon Is a Harsh Mistress",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                new HashSet<>(mongoOperations.find(Query.query(Criteria.where("fio").is("Robert Hainline")), Author.class))));

        bookRepository.save(new Book(null, "Prisoners of Power",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Fantasy")), Genre.class),
                new HashSet<>(mongoOperations.find(Query.query(Criteria.where("fio").is("Arkady and Boris Strugatsky")), Author.class))));

        bookRepository.save(new Book(null, "Prisoners of Power, Blindsight",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                new HashSet<>(mongoOperations.find(Query.query(Criteria.where("fio").in("Peter Watts", "Arkady and Boris Strugatsky")), Author.class))));

        bookRepository.save(new Book(null, "The Moon Is a Harsh Mistress, Prisoners of Power",
                mongoOperations.findOne(Query.query(Criteria.where("caption").is("Sci-Fi")), Genre.class),
                new HashSet<>(mongoOperations.find(Query.query(Criteria.where("fio").in("Robert Hainline","Arkady and Boris Strugatsky")), Author.class))));
    }

    /*@ChangeSet(order = "002", id = "initStudents", author = "stvort", runAlways = true)
    public void initStudents(MongoTemplate template){
        template.save(new Student("Student #1", springDataKnowledge, mongockKnowledge));
    }

    @ChangeSet(order = "003", id = "Teacher", author = "stvort", runAlways = true)
    public void initTeachers(MongoTemplate template){
        val teacher = new Teacher("Teacher #1", springDataKnowledge, mongockKnowledge, aggregationApiKnowledge);
        template.save(teacher);
    }*/
}
