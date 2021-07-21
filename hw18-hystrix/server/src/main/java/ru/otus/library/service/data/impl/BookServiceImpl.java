package ru.otus.library.service.data.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.data.BookService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Secured({"ROLE_ADMIN"})
    @HystrixCommand(fallbackMethod = "fallbackCreateOrUpdate")
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

    private BookDto fallbackCreateOrUpdate(BookDto bookDto) {
        return bookDto;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackAddComment")
    public BookDto addComment(String idBook, String commentCaption) {
        Book book = bookRepository.findById(idBook).orElseThrow(EntityNotFoundException::new);

        if (Objects.isNull(book.getComments())) {
            book.setComments(new ArrayList<>());
        }

        book.getComments().add(new Comment(commentCaption));
        return new BookDto(bookRepository.save(book));
    }

    private BookDto fallbackCreateOrUpdate(String idBook, String commentCaption) {
        return new BookDto();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackRemoveComment")
    public BookDto removeComment(String idBook, String idComment) {
        final Book book = bookRepository.findById(idBook).orElseThrow(EntityNotFoundException::new);
        List<Comment> newComments = book.getComments()
                .stream().filter(comment -> !comment.getId().equals(idComment))
                .collect(Collectors.toList());

        book.setComments(newComments);
        return new BookDto(bookRepository.save(book));
    }

    private BookDto fallbackRemoveComment(String idBook, String commentCaption) {
        return new BookDto();
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackCreateOrUpdateBook")
    public Book createOrUpdate(Book book) {
        return bookRepository.save(book);
    }

    private Book fallbackCreateOrUpdateBook(Book book) {
        return book;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetById")
    public Book getById(String id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    private Book fallbackGetById(String id) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetAll")
    public Collection<Book> getAll() {
        return bookRepository.findAll();
    }

    private Collection<Book> fallbackGetAll() {
        return Collections.emptyList();
    }

    @Override
    public void removeById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetByIdDto")
    public BookDto getByIdDto(String id) {
        return new BookDto(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    private BookDto fallbackGetByIdDto(String id) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetAllDto")
    public Collection<BookDto> getAllDto() {
        return this.getAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    private Collection<BookDto> fallbackGetAllDto() {
        return Collections.emptyList();
    }
}
