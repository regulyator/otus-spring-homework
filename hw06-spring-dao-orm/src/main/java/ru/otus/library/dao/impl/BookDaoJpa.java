package ru.otus.library.dao.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isExistById(long id) {
        return entityManager.createQuery("select count(b.id) from Book b where b.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0L;
    }

    @Override
    public Book save(@NonNull Book book) {
        if (book.getId() <= 0) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            return Optional.ofNullable(entityManager
                    .createQuery("select b from Book b left join fetch b.comments left join fetch b.genre where b.id = :id", Book.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Book> findAll() {
        return entityManager
                .createQuery("select distinct b from Book b left join fetch b.comments left join fetch b.genre ", Book.class)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
