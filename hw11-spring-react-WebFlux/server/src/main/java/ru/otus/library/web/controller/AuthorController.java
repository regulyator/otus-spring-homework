package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.data.AuthorService;

import java.util.Collection;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/library/api/authors")
    public ResponseEntity<Collection<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @PutMapping("/library/api/authors")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.createOrUpdate(author));
    }

    @PostMapping("/library/api/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.createOrUpdate(author);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(createdAuthor.getId()).toUri()).body(createdAuthor);
    }

    @DeleteMapping("/library/api/authors/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String authorId) {
        authorService.removeById(authorId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ReferenceEntityException.class})
    public ResponseEntity<String> referenceDeleteErrorHandler() {
        return new ResponseEntity<>("Reference delete error! First remove this author from Books!", HttpStatus.BAD_REQUEST);
    }
}
