package ru.otus.library.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.data.GenreService;
import ru.otus.library.service.util.InputOutputComponent;

@ShellComponent
@ShellCommandGroup("Genre commands")
public class LibraryAppShellGenre {
    private final InputOutputComponent inputOutputComponent;
    private final GenreService genreService;

    @Autowired
    public LibraryAppShellGenre(InputOutputComponent inputOutputComponent,
                                GenreService genreService) {
        this.inputOutputComponent = inputOutputComponent;
        this.genreService = genreService;
    }

    @ShellMethod(key = "genres-create", value = "Create new genre")
    public void createGenre(@ShellOption({"GenreCaption"}) String genreCaption) {
        genreService.create(genreCaption);
    }

    @ShellMethod(key = "genres-all", value = "Show all genres")
    public void getAllGenres() {
        genreService.getAll().forEach(author -> inputOutputComponent.writeOutput(author.toString()));
    }

    @ShellMethod(key = "genres", value = "Show genre by id")
    public void getGenre(@ShellOption({"GenreID"}) long genreId) {
        inputOutputComponent.writeOutput(genreService.getById(genreId).toString());
    }

    @ShellMethod(key = "genres-update", value = "Update genre")
    public void updateGenre(@ShellOption({"GenreID"}) long genreId,
                            @ShellOption({"GenreCaption"}) String genreCaption) {
        genreService.changeGenreCaption(genreId, genreCaption);
    }

    @ShellMethod(key = "genres-remove", value = "Remove genre by id")
    public void removeGenre(@ShellOption({"GenreID"}) long genreId) {
        try {
            genreService.removeById(genreId);
        } catch (DataIntegrityViolationException ex) {
            inputOutputComponent.writeOutput("Error! Can't delete genre referenced to book!");
        }
    }


}
