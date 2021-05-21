package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.service.data.AuthorService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/library/api")
public class AuthorController {
    private static final String REDIRECT_AUTHORS = "redirect:/authors";
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/authors")
    public Collection<Author> getAllAuthors() {
        return authorService.getAll();
    }

    /*@GetMapping("/authors/{authorId}/delete")
    public String deleteAuthor(@PathVariable String authorId) {
        authorService.removeById(authorId);
        return REDIRECT_AUTHORS;
    }

    @PostMapping("/authors")
    public String updateAuthor(@ModelAttribute Author author) {
        authorService.changeAuthorFio(author.getId(), author.getFio());
        return REDIRECT_AUTHORS;
    }

    @PutMapping("/authors")
    public String createAuthor(@RequestParam String newAuthorFio) {
        authorService.create(newAuthorFio);
        return REDIRECT_AUTHORS;
    }

    @ExceptionHandler({ReferenceEntityException.class})
    public String referenceDeleteErrorHandler(Model model) {
        model.addAttribute("entity", "Author");
        return "Reference error";
    }*/
}
