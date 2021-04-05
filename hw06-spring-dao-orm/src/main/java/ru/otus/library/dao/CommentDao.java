package ru.otus.library.dao;

import ru.otus.library.domain.Comment;

import java.util.Collection;
import java.util.Optional;

public interface CommentDao {

    boolean isExistById(long id);

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    Collection<Comment> findAll();

    void deleteById(long id);
}
