package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.CommentService;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @Autowired
    public BookServiceImpl(BookDao bookDao,
                           AuthorService authorService,
                           GenreService genreService,
                           CommentService commentService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @Override
    @Transactional
    public Book create(String bookName, long idGenre, long[] idAuthors) {
        Set<Author> authors = new HashSet<>(authorService.getAll(LongStream.of(idAuthors).boxed().collect(Collectors.toList())));
        Genre genre = genreService.getById(idGenre);

        Book newBook = new Book();
        newBook.setBookName(bookName);
        newBook.setGenre(genre);
        newBook.setAuthors(authors);

        return bookDao.save(newBook);
    }

    @Override
    @Transactional
    public Book changeBookName(long idBook, String newBookName) {
        Book book = bookDao.findById(idBook).orElseThrow(EntityNotFoundException::new);
        book.setBookName(newBookName);
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book changeBookGenre(long idBook, long newIdGenre) {
        Book book = bookDao.findById(idBook).orElseThrow(EntityNotFoundException::new);
        Genre genre = genreService.getById(newIdGenre);
        book.setGenre(genre);
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book addBookAuthor(long idBook, long idAuthor) {
        Book book = bookDao.findById(idBook).orElseThrow(EntityNotFoundException::new);
        Author addedAuthor = authorService.getById(idAuthor);
        book.getAuthors().add(addedAuthor);
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book removeBookAuthor(long idBook, long idAuthor) {
        Book book = bookDao.findById(idBook).orElseThrow(EntityNotFoundException::new);
        Author removedAuthor = authorService.getById(idAuthor);
        book.getAuthors().remove(removedAuthor);
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book addBookComment(long idBook, String newCommentCaption) {
        Book book = bookDao.findById(idBook).orElseThrow(EntityNotFoundException::new);

        Comment newComment = new Comment();
        newComment.setCaption(newCommentCaption);
        book.getComments().add(newComment);

        return bookDao.save(book);
    }

    @Override
    @Transactional
    public Book removeBookComment(long idBook, long idComment) {
        Book book = bookDao.findById(idBook).orElseThrow(EntityNotFoundException::new);

        Comment removedComment = commentService.getById(idComment);
        book.getComments().remove(removedComment);

        return bookDao.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistById(long id) {
        return bookDao.isExistById(id);
    }

    @Override
    @Transactional
    public Book createOrUpdate(Book book) {
        return bookDao.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return bookDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    @Transactional
    public void removeById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getByIdDto(long id) {
        return new BookDto(bookDao.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookDto> getAllDto() {
        return this.getAll().stream().map(BookDto::new).collect(Collectors.toList());
    }
}
