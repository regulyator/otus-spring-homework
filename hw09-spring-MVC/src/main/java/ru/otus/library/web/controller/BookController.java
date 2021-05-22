package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;

@Controller
public class BookController {
    private static final String REDIRECT_BOOKS = "redirect:/books/";
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService,
                          GenreService genreService,
                          AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllDto());
        return "Books";
    }

    @GetMapping("/books/{bookId}")
    public String getBook(Model model, @PathVariable String bookId) {
        BookDto bookDto = bookService.getByIdDto(bookId);
        Collection<Author> authors = authorService.getAll();
        authors.removeAll(bookDto.getAuthors());

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authors);
        model.addAttribute("comments", bookDto.getComments());
        return "Book";
    }

    @GetMapping("/books/new")
    public String createBook(Model model) {
        BookDto newBook = new BookDto();
        model.addAttribute("bookDto", newBook);
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "Book";
    }

    @PostMapping("/books")
    public String createOrUpdateBook(@ModelAttribute BookDto bookDto) {
        bookService.createOrUpdateAndSetIdIfNew(bookDto);
        return REDIRECT_BOOKS + bookDto.getId();
    }

    @DeleteMapping("/books/{bookId}")
    public String deleteBook(@PathVariable String bookId) {
        bookService.removeById(bookId);
        return REDIRECT_BOOKS;
    }

    @PutMapping("/books/{bookId}/comment")
    public String addCommentToBook(@PathVariable String bookId,
                                   @RequestParam String newCommentText) {
        bookService.addComment(bookId, newCommentText);
        return REDIRECT_BOOKS + bookId;
    }

    @PutMapping("/books/{bookId}/comment/{commentId}")
    public String deleteComment(@PathVariable String bookId,
                                @PathVariable String commentId) {
        bookService.removeCommentFromBook(bookId, commentId);
        return REDIRECT_BOOKS + bookId;
    }

    @PutMapping("/books/{bookId}/author")
    public String addAuthorToBook(@PathVariable String bookId,
                                  @RequestParam String authorId) {
        bookService.addBookAuthor(bookId, authorId);
        return REDIRECT_BOOKS + bookId;
    }

    @PutMapping("/books/{bookId}/author/{authorId}")
    public String deleteAuthorFromBook(@PathVariable String bookId,
                                       @PathVariable String authorId) {
        bookService.removeBookAuthor(bookId, authorId);
        return REDIRECT_BOOKS + bookId;
    }


}
