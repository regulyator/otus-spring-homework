package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Collection<Author> findAllByIdIn(List<Long> ids);

}
