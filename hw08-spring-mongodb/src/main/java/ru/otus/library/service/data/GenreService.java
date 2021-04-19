package ru.otus.library.service.data;

import ru.otus.library.domain.Genre;

public interface GenreService extends StandardService<Genre> {

    Genre create(String genreCaption);

    Genre changeGenreCaption(String idGenre, String newGenreCaption);
}
