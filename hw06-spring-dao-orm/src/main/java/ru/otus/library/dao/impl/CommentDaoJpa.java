package ru.otus.library.dao.impl;

import ru.otus.library.dao.CommentDao;
import ru.otus.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean isExistById(long id) {
        return false;
    }

    @Override
    public long insert(Author entity) {
        return 0;
    }

    @Override
    public void update(Author entity) {

    }

    @Override
    public Author findById(long id) {
        return null;
    }

    @Override
    public Collection<Author> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
