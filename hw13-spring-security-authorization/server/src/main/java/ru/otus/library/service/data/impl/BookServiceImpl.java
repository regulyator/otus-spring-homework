package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public BookDto addComment(String idBook, String commentCaption) {
        Book book = bookRepository.findById(idBook).orElseThrow(EntityNotFoundException::new);

        if (Objects.isNull(book.getComments())) {
            book.setComments(new ArrayList<>());
        }

        book.getComments().add(new Comment(commentCaption));
        return new BookDto(bookRepository.save(book));
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
}
