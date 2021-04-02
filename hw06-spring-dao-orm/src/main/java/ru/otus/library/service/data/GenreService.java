package ru.otus.library.service.data;

import ru.otus.library.domain.Genre;

public interface GenreService extends StandardService<Genre> {

    boolean checkExistById(long id);

    Genre create(String newGenreCaption);
}
