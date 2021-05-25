package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Author;
import ru.otus.library.service.data.AuthorService;

import java.util.Collection;

@RestController
@RequestMapping("/library/api")
public class AuthorController {
    private static final String REDIRECT_AUTHORS = "redirect:/authors";
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @PutMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestParam String newAuthorFio) {
        return ResponseEntity.ok(authorService.create(newAuthorFio));
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.changeAuthorFio(author.getId(), author.getFio()));
    }

    @DeleteMapping("/authors")
    public ResponseEntity<?> deleteAuthor(@RequestParam String authorId) {
        authorService.removeById(authorId);
        return ResponseEntity.ok().build();
    }

    /*
    @ExceptionHandler({ReferenceEntityException.class})
    public String referenceDeleteErrorHandler(Model model) {
        model.addAttribute("entity", "Author");
        return "Reference error";
    }*/
}
