package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;

@Controller
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService,
                          GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping("/books{bookId}")
    public String bookEdit(Model model, @PathVariable String bookId) {
        model.addAttribute("bookDto", bookService.getByIdDto(bookId));
        model.addAttribute("genres", genreService.getAll());
        return "Book";
    }

    @PostMapping("/books")
    public String saveBook(Model model, @ModelAttribute BookDto bookDto) {
        bookService.changeBookName(bookDto.getId(), bookDto.getBookName());
        bookService.changeBookGenre(bookDto.getId(), bookDto.getGenre().getId());
        model.addAttribute("bookDto", bookService.getByIdDto(bookDto.getId()));
        model.addAttribute("genres", genreService.getAll());
        return "Book";
    }


}
