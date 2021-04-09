package ru.otus.library.dao;

import java.util.Collection;
import java.util.Optional;

public interface StandardDao<T> {

    boolean isExistById(long id);

    T save(T entity);

    Optional<T> findById(long id);

    Collection<T> findAll();

    void deleteById(long id);
}
