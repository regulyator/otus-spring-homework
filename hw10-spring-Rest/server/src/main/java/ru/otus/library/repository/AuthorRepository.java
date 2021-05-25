package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findAllByIdIn(Collection<String> iDs);
}
