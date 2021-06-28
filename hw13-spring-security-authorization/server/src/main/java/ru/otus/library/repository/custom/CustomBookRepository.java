package ru.otus.library.repository.custom;

import ru.otus.library.domain.Genre;

public interface CustomBookRepository {

    void updateBooksGenre(Genre newGenre);
}
