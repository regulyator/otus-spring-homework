package ru.otus.library.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.util.InputOutputComponent;

@ShellComponent
@ShellCommandGroup("Author commands")
public class LibraryAppShellAuthor {
    private final InputOutputComponent inputOutputComponent;
    private final AuthorService authorService;

    @Autowired
    public LibraryAppShellAuthor(InputOutputComponent inputOutputComponent,
                                 AuthorService authorService) {
        this.inputOutputComponent = inputOutputComponent;
        this.authorService = authorService;
    }

    @ShellMethod(key = "authors-create", value = "Create new author")
    public void createAuthor(@ShellOption({"AuthorFio"}) String authorFio) {
        authorService.create(authorFio);
    }

    @ShellMethod(key = "authors-all", value = "Show all authors")
    public void getAllAuthors() {
        authorService.getAll().forEach(author -> inputOutputComponent.writeOutput(author.toString()));
    }

    @ShellMethod(key = "authors", value = "Show author by id")
    public void getAuthor(@ShellOption({"AuthorID"}) long authorId) {
        inputOutputComponent.writeOutput(authorService.getById(authorId).toString());
    }

    @ShellMethod(key = "authors-update", value = "Update author")
    public void updateAuthor(@ShellOption({"AuthorID"}) long authorId,
                             @ShellOption({"AuthorFio"}) String authorFio) {
        authorService.changeAuthorFio(authorId, authorFio);
    }

    @ShellMethod(key = "authors-remove", value = "Remove author by id")
    public void removeAuthor(@ShellOption({"AuthorID"}) long authorId) {
        try {
            authorService.removeById(authorId);
        } catch (DataIntegrityViolationException ex) {
            inputOutputComponent.writeOutput("Error! Can't delete author referenced to book!");
        }
    }


}
