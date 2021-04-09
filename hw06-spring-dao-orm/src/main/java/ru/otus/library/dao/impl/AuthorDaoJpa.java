package ru.otus.library.dao.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
@Repository
public class AuthorDaoJpa implements AuthorDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isExistById(long id) {
        return entityManager.createQuery("select count(a.id) from Author a where a.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0L;
    }

    @Override
    public Author save(@NonNull Author author) {
        if (author.getId() <= 0L) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public Collection<Author> findAll(Collection<Long> authorsIds) {
        return entityManager.createQuery("select a from Author a where a.id in :ids", Author.class)
                .setParameter("ids", authorsIds)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Author a where a.id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }


}
