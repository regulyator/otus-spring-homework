package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.custom.CustomBookRepository;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, CustomBookRepository {

    Mono<Book> findByBookName(String bookName);

    boolean existsBookByGenre_Id(String genreId);

    boolean existsBookByAuthors_Id(String authorId);

    Flux<Book> findAllByGenre_Id(String genreId);
}
