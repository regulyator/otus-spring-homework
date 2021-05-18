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

@Controller
public class BookController {
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

    @GetMapping("/books/{bookId}")
    public String getBook(Model model, @PathVariable String bookId) {
        model.addAttribute("bookDto", bookService.getByIdDto(bookId));
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        return "Book";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute BookDto bookDto) {
        bookService.changeBookName(bookDto.getId(), bookDto.getBookName());
        bookService.changeBookGenre(bookDto.getId(), bookDto.getGenre().getId());
        return "redirect:/books/" + bookDto.getId();
    }

    @PutMapping("/books/{bookId}/comment")
    public String addCommentToBook(@PathVariable String bookId,
                                   @RequestParam String newCommentText) {
        bookService.addComment(bookId, newCommentText);
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("/books/{bookId}/comment/{commentId}")
    public String deleteComment(@PathVariable String bookId,
                                   @PathVariable String commentId) {
        bookService.removeCommentFromBook(bookId, commentId);
        return "redirect:/books/" + bookId;
    }

    @PutMapping("/books/{bookId}/author")
    public String addAuthorToBook(@PathVariable String bookId,
                                   @RequestParam String authorId) {
        bookService.addBookAuthor(bookId, authorId);
        return "redirect:/books/" + bookId;
    }


}
