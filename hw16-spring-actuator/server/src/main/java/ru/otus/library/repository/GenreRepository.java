package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.library.domain.Genre;

@RepositoryRestResource(collectionResourceRel = "genres", path = "genres")
public interface GenreRepository extends MongoRepository<Genre, String> {

}
