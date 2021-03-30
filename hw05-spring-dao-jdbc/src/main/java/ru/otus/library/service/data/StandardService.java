package ru.otus.library.service.data;

import java.util.Collection;

public interface StandardService<T> {

    void update(T entity);

    T getById(long id);

    Collection<T> getAll();

    void removeById(long id);
}
