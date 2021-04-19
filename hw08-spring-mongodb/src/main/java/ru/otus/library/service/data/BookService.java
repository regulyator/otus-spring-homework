package ru.otus.library.service.data;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.dto.BookDto;

import java.util.Collection;

public interface BookService extends StandardService<Book> {

    Book create(String bookName, String idGenre, Collection<String> idAuthors);

    Book changeBookName(String idBook, String newBookName);

    Book changeBookGenre(String idBook, String newIdGenre);

    Book addBookAuthor(String idBook, String idAuthor);

    Book removeBookAuthor(String idBook, String idAuthor);

    BookDto getByIdDto(String id);

    Collection<BookDto> getAllDto();


}
