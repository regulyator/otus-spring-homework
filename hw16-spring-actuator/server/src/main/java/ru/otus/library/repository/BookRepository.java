package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.custom.CustomBookRepository;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface BookRepository extends MongoRepository<Book, String>, CustomBookRepository {

    Optional<Book> findByBookName(String bookName);

    boolean existsBookByGenre_Id(String genreId);

    boolean existsBookByAuthors_Id(String authorId);

    List<Book> findAllByGenre_Id(String genreId);
}