package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;

@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/library/api/genres")
    public ResponseEntity<Collection<Genre>> getAllGenre() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @PutMapping("/library/api/genres")
    public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.createOrUpdate(genre));
    }

    @PostMapping("/library/api/genres")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.createOrUpdate(genre));
    }

    @DeleteMapping("/library/api/genres/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable String genreId) {
        genreService.removeById(genreId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({ReferenceEntityException.class})
    public ResponseEntity<String> referenceDeleteErrorHandler() {
        return new ResponseEntity<>("Reference delete error! First remove this genre from Books!", HttpStatus.BAD_REQUEST);
    }
}
