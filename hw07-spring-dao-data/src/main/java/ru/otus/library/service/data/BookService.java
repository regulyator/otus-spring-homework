package ru.otus.library.service.data;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.dto.BookDto;

import java.util.Collection;

public interface BookService extends StandardService<Book> {

    Book create(String bookName, long idGenre, long[] idAuthors);

    Book changeBookName(long idBook, String newBookName);

    Book changeBookGenre(long idBook, long newIdGenre);

    Book addBookAuthor(long idBook, long idAuthor);

    Book removeBookAuthor(long idBook, long idAuthor);

    BookDto getByIdDto(long id);

    Collection<BookDto> getAllDto();


}
