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

    /*@ShellMethod(key = "books-create", value = "Create new book with Authors and Genre")
    public void createBook(@ShellOption({"BookName"}) String bookName,
                           @ShellOption({"GenreId"}) long genreId,
                           @ShellOption({"AuthorsIds"}) long[] authorsIds
    ) {
        bookService.create(bookName, genreId, authorsIds);
    }

    @ShellMethod(key = "books", value = "Show book by id")
    public void getBook(@ShellOption({"BookID"}) long bookId) {
        inputOutputComponent.writeOutput(bookService.getByIdDto(bookId).toString());
    }

    @ShellMethod(key = "books-all", value = "Show all books")
    public void getAllBooks() {
        bookService.getAllDto().forEach(book -> inputOutputComponent.writeOutput(book.toString()));
    }

    @ShellMethod(key = "books-update-name", value = "Update book name")
    public void updateBookName(@ShellOption({"BookID"}) long bookId,
                               @ShellOption({"BookName"}) String bookName) {
        bookService.changeBookName(bookId, bookName);
    }

    @ShellMethod(key = "books-update-genre", value = "Update book genre")
    public void updateBookName(@ShellOption({"BookID"}) long bookId,
                               @ShellOption({"GenreId"}) long genreId) {
        bookService.changeBookGenre(bookId, genreId);
    }

    @ShellMethod(key = "books-add-author", value = "Add author to book")
    public void addBookAuthor(@ShellOption({"BookID"}) long bookId,
                              @ShellOption({"AuthorsId"}) long authorsId) {
        bookService.addBookAuthor(bookId, authorsId);
    }

    @ShellMethod(key = "books-remove-author", value = "Remove author from book")
    public void removeBookAuthor(@ShellOption({"BookID"}) long bookId,
                                 @ShellOption({"AuthorsId"}) long authorsId) {
        bookService.removeBookAuthor(bookId, authorsId);
    }

    @ShellMethod(key = "books-remove", value = "Remove book by id")
    public void removeBook(@ShellOption({"BookID"}) long bookId) {
        bookService.removeById(bookId);
    }*/


}
