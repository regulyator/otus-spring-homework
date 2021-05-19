package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.library.service.data.BookService;

@Controller
public class WelcomeController {
    private final BookService bookService;

    @Autowired
    public WelcomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String main(Model model) {
        return "Welcome";
    }
}
