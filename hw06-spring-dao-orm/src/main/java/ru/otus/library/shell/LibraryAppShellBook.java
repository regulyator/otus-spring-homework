package ru.otus.library.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.util.InputOutputComponent;

@ShellComponent
@ShellCommandGroup("Book commands")
public class LibraryAppShellBook {
    private final InputOutputComponent inputOutputComponent;
    private final BookService bookService;

    @Autowired
    public LibraryAppShellBook(InputOutputComponent inputOutputComponent,
                               BookService bookService) {
        this.inputOutputComponent = inputOutputComponent;
        this.bookService = bookService;
    }

    @ShellMethod(key = "books-create", value = "Create new book with new Author and Genre")
    public void createBook(@ShellOption({"BookName"}) String bookName,
                           @ShellOption({"AuthorFio"}) String authorFio,
                           @ShellOption({"GenreCaption"}) String genreCaption) {
        bookService.create(bookName, authorFio, genreCaption);
    }

    @ShellMethod(key = "books-create-er", value = "Create new book with existing Author and Genre")
    public void createBook(@ShellOption({"BookName"}) String bookName,
                           @ShellOption({"AuthorID"}) long authorId,
                           @ShellOption({"GenreID"}) long genreId) {
        bookService.create(bookName, authorId, genreId);
    }

    @ShellMethod(key = "books", value = "Show book by id")
    public void getBook(@ShellOption({"BookID"}) long bookId) {
        inputOutputComponent.writeOutput(bookService.getById(bookId).toString());
    }

    @ShellMethod(key = "books-all", value = "Show all books")
    public void getAllBooks() {
        bookService.getAll().forEach(book -> inputOutputComponent.writeOutput(book.toString()));
    }

    @ShellMethod(key = "books-update", value = "Update book with existing Author and Genre")
    public void updateBook(@ShellOption({"BookID"}) long bookId,
                           @ShellOption({"BookName"}) String bookName,
                           @ShellOption({"AuthorID"}) long authorId,
                           @ShellOption({"GenreID"}) long genreId) {
        bookService.update(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(key = "books-remove", value = "Remove book by id")
    public void removeBook(@ShellOption({"BookID"}) long bookId) {
        bookService.removeById(bookId);
    }


}
