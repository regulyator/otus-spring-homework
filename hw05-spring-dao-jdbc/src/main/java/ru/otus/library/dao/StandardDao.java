package ru.otus.library.dao;

import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.Collection;

public interface StandardDao<T> {

    long insert(T entity);

    void update(T entity);

    T findById(long id);

    Collection<T> findAll();

    void deleteById(long id);

    default void checkIdForInsert(long id) {
        if (id != 0L) {
            throw new DaoInsertNonEmptyIdException("Call insert for non empty ID field!");
        }
    }

    default void checkIdForUpdate(long id) {
        if (id == 0L) {
            throw new DaoUpdateEmptyIdException("Call update for empty ID field!");
        }
    }
}
