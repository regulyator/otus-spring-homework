package ru.otus.library.service.data;

import java.util.Collection;

public interface StandardService<T> {

    boolean checkExistById(long id);

    T createOrUpdate(T entity);

    T getById(long id);

    Collection<T> getAll();

    void removeById(long id);
}
