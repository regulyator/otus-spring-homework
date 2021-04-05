package ru.otus.library.dao.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.CommentDao;
import ru.otus.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isExistById(long id) {
        return entityManager.createQuery("select count(c.id) from Comment c where c.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0L;
    }

    @Override
    public Comment save(@NonNull Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public Collection<Comment> findAll() {
        return entityManager.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Comment c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
