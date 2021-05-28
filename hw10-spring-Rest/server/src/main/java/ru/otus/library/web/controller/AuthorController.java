package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.service.data.AuthorService;

import java.util.Collection;

@RestController
@RequestMapping("/library/api")
public class AuthorController {
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
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.createOrUpdate(author));
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.createOrUpdate(author));
    }

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String authorId) {
        authorService.removeById(authorId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ReferenceEntityException.class})
    public ResponseEntity<String> referenceDeleteErrorHandler() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reference delete error! First remove this author from Books!");
    }
}
