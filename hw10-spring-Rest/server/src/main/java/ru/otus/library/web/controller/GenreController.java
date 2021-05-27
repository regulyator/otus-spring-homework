package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.service.data.GenreService;

@Controller
public class GenreController {
    private static final String REDIRECT_GENRES = "redirect:/genres";
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "Genres";
    }

    @DeleteMapping("/genres/{genreId}/delete")
    public String deleteGenre(@PathVariable String genreId) {
        genreService.removeById(genreId);
        return REDIRECT_GENRES;
    }

    @PutMapping("/genres")
    public String updateGenre(@ModelAttribute Genre genre) {
        genreService.createOrUpdate(genre);
        return REDIRECT_GENRES;
    }

    @PostMapping("/genres")
    public String createGenre(@ModelAttribute Genre genre) {
        genreService.createOrUpdate(genre);
        return REDIRECT_GENRES;
    }

    @ExceptionHandler({ReferenceEntityException.class})
    public String referenceDeleteErrorHandler(Model model) {
        model.addAttribute("entity", "Genre");
        return "Reference error";
    }
}
