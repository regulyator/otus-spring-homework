package ru.otus.library.repository.custom;

import ru.otus.library.domain.Book;

public interface CustomBookRepository {

    Book deleteBookComment(String idBook, String idComment);
}
