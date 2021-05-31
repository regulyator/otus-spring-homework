package ru.otus.library.service.data;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.dto.BookDto;

import java.util.Collection;

public interface BookService extends StandardService<Book> {

    BookDto createOrUpdate(BookDto bookDto);

    BookDto addComment(String idBook, String commentCaption);

    BookDto getByIdDto(String id);

    Collection<BookDto> getAllDto();


}
