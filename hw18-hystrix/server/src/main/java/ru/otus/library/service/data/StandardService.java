package ru.otus.library.service.data;

import java.util.Collection;

public interface StandardService<T> {

    T createOrUpdate(T entity);

    T getById(String id);

    Collection<T> getAll();

    void removeById(String id);
}
