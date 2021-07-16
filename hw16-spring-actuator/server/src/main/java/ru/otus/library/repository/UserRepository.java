package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.library.domain.User;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
