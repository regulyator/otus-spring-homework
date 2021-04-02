package ru.otus.library.dao;

import java.util.Collection;

public interface StandardDao<T> {

    boolean isExistById(long id);

    long insert(T entity);

    void update(T entity);

    T findById(long id);

    Collection<T> findAll();

    void deleteById(long id);
}
