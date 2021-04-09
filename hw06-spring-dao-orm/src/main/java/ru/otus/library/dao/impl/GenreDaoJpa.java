package ru.otus.library.dao.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ConstantConditions")
@Repository
public class GenreDaoJpa implements GenreDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isExistById(long id) {
        return entityManager.createQuery("select count(g.id) from Genre g where g.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult() > 0L;
    }

    @Override
    public Genre save(@NonNull Genre genre) {
        if (genre.getId() <= 0L) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Genre g where g.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
