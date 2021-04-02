package ru.otus.library.service.data;

import ru.otus.library.domain.Book;

public interface BookService extends StandardService<Book> {

    Book create(String bookName, String authorFio, String genreCaption);

    Book create(String bookName, long authorId, long genreId);

    void update(long bookId, String bookName, long authorId, long genreId);
}
