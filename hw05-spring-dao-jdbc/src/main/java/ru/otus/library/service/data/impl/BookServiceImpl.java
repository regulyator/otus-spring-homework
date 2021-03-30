package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.NoSuchReferenceIdException;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Book create(String bookName, String authorFio, String genreCaption) {
        final Author newAuthor = authorService.create(authorFio);
        final Genre newGenre = genreService.create(genreCaption);
        return create(new Book(0L, bookName, newAuthor, newGenre));
    }

    @Override
    public Book create(String bookName, long authorId, long genreId) {
        final Author author = new Author(authorId, "");
        final Genre genre = new Genre(genreId, "");
        final Book newBook = new Book(0L, bookName, author, genre);
        checkReferenceId(newBook);
        return create(newBook);
    }

    @Override
    public void update(Book book) {
        bookDao.update(book);
    }

    @Override
    public void update(long bookId, String bookName, long authorId, long genreId) {
        final Author author = new Author(authorId, "");
        final Genre genre = new Genre(genreId, "");
        final Book updatedBook = new Book(bookId, bookName, author, genre);
        checkReferenceId(updatedBook);
        update(updatedBook);
    }

    @Override
    public Book getById(long id) {
        return bookDao.findById(id);
    }

    @Override
    public Collection<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public void removeById(long id) {
        bookDao.deleteById(id);
    }

    private void checkReferenceId(Book book) {
        final boolean isAuthorIdExist = authorService.checkExistById(book.getAuthor().getId());
        final boolean isGenreIdExist = genreService.checkExistById(book.getGenre().getId());
        if (!isAuthorIdExist || !isGenreIdExist) {
            throw new NoSuchReferenceIdException(String.format("No reference entity exist: %s %s",
                    !isAuthorIdExist ? "Author" : "",
                    !isGenreIdExist ? "Genre" : ""));
        }
    }

    private Book create(Book book) {
        checkReferenceId(book);
        final long generatedId = bookDao.insert(book);
        book.setId(generatedId);
        return book;
    }
}
