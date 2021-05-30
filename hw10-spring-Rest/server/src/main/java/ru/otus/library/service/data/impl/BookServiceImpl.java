package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public BookDto createOrUpdate(BookDto bookDto) {
        if (Objects.isNull(bookDto.getId())) {
            Book createdBook = bookRepository.save(new Book(bookDto));
            bookDto.setId(createdBook.getId());
        } else {
            Book book = bookRepository.findById(bookDto.getId()).orElseThrow(EntityNotFoundException::new);
            book.setBookName(bookDto.getBookName());
            book.setGenre(bookDto.getGenre());
            book.setAuthors(bookDto.getAuthors());
            book.setComments(bookDto.getComments());
            bookRepository.save(book);
        }

        return bookDto;
    }

    @Override
    public Book addBookAuthor(String idBook, String idAuthor) {
        Book book = bookRepository.findById(idBook).orElseThrow(EntityNotFoundException::new);
        Author addedAuthor = authorService.getById(idAuthor);
        book.getAuthors().add(addedAuthor);
        return bookRepository.save(book);
    }

    @Override
    public Book removeBookAuthor(String idBook, String idAuthor) {
        Book book = bookRepository.findById(idBook).orElseThrow(EntityNotFoundException::new);
        Author removedAuthor = authorService.getById(idAuthor);
        book.getAuthors().remove(removedAuthor);
        return bookRepository.save(book);
    }

    @Override
    public Book addComment(String idBook, String commentCaption) {
        Book book = bookRepository.findById(idBook).orElseThrow(EntityNotFoundException::new);

        if (Objects.isNull(book.getComments())) {
            book.setComments(new ArrayList<>());
        }

        book.getComments().add(new Comment(commentCaption));
        return bookRepository.save(book);
    }

    @Override
    public Book removeCommentFromBook(String idBook, String idComment) {
        return bookRepository.deleteBookComment(idBook, idComment);
    }

    @Override
    public boolean checkExistById(String id) {
        return bookRepository.existsById(id);
    }

    @Override
    public Book createOrUpdate(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Collection<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void removeById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto getByIdDto(String id) {
        return new BookDto(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Collection<BookDto> getAllDto() {
        return this.getAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getAllBookComment(String id) {
        return getById(id).getComments();
    }
}
