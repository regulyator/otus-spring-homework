package ru.otus.library.service.data;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.BookDto;

import java.util.Collection;
import java.util.List;

public interface BookService extends StandardService<Book> {

    Book create(String bookName, String idGenre, Collection<String> idAuthors);

    BookDto createOrUpdateAndSetIdIfNew(BookDto bookDto);

    Book addBookAuthor(String idBook, String idAuthor);

    Book removeBookAuthor(String idBook, String idAuthor);

    Book addComment(String idBook, String commentCaption);

    Book removeCommentFromBook(String idBook, String idComment);

    BookDto getByIdDto(String id);

    Collection<BookDto> getAllDto();

    List<Comment> getAllBookComment(String id);


}
