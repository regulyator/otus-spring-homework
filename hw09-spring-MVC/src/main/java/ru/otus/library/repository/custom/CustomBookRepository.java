package ru.otus.library.repository.custom;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

public interface CustomBookRepository {

    Book deleteBookComment(String idBook, String idComment);

    void updateBooksGenre(Genre newGenre);
}
