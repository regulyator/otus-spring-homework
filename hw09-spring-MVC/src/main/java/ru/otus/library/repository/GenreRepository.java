package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
