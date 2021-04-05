package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
