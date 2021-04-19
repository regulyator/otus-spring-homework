package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
