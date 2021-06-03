package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/library/api/books")
    public ResponseEntity<Collection<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllDto());
    }

    @GetMapping("/library/api/books/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable String bookId) {
        return ResponseEntity.ok(bookService.getByIdDto(bookId));
    }

    @PutMapping("/library/api/books")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto book) {
        return ResponseEntity.ok(bookService.createOrUpdate(book));
    }

    @PostMapping("/library/api/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto book) {
        return ResponseEntity.ok(bookService.createOrUpdate(book));
    }

    @PutMapping("/library/api/books/{bookId}/comment")
    public ResponseEntity<BookDto> addCommentToBook(@PathVariable String bookId,
                                                    @RequestParam String newCommentText) {
        return ResponseEntity.ok(bookService.addComment(bookId, newCommentText));
    }

    @DeleteMapping("/library/api/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        bookService.removeById(bookId);
        return ResponseEntity.ok().build();
    }


}
