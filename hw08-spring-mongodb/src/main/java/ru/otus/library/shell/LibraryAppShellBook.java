package ru.otus.library.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.util.InputOutputComponent;

import java.util.Collection;

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

    @ShellMethod(key = "books-create", value = "Create new book with Authors and Genre")
    public void createBook(@ShellOption({"BookName"}) String bookName,
                           @ShellOption({"GenreId"}) String genreId,
                           @ShellOption({"AuthorsIds"}) Collection<String> authorsIds
    ) {
        bookService.create(bookName, genreId, authorsIds);
    }

    @ShellMethod(key = "books", value = "Show book by id")
    public void getBook(@ShellOption({"BookID"}) String bookId) {
        inputOutputComponent.writeOutput(bookService.getByIdDto(bookId).toString());
    }

    @ShellMethod(key = "books-all", value = "Show all books")
    public void getAllBooks() {
        bookService.getAllDto().forEach(book -> inputOutputComponent.writeOutput(book.toString()));
    }

    @ShellMethod(key = "books-update-name", value = "Update book name")
    public void updateBookName(@ShellOption({"BookID"}) String bookId,
                               @ShellOption({"BookName"}) String bookName) {
        bookService.changeBookName(bookId, bookName);
    }

    @ShellMethod(key = "books-update-genre", value = "Update book genre")
    public void updateBookGenre(@ShellOption({"BookID"}) String bookId,
                                @ShellOption({"GenreId"}) String genreId) {
        bookService.changeBookGenre(bookId, genreId);
    }

    @ShellMethod(key = "books-add-author", value = "Add author to book")
    public void addBookAuthor(@ShellOption({"BookID"}) String bookId,
                              @ShellOption({"AuthorsId"}) String authorsId) {
        bookService.addBookAuthor(bookId, authorsId);
    }

    @ShellMethod(key = "books-remove-author", value = "Remove author from book")
    public void removeBookAuthor(@ShellOption({"BookID"}) String bookId,
                                 @ShellOption({"AuthorsId"}) String authorsId) {
        bookService.removeBookAuthor(bookId, authorsId);
    }

    @ShellMethod(key = "books-add-comment", value = "Add comment to book")
    public void addBookComment(@ShellOption({"BookID"}) String bookId,
                               @ShellOption({"Comment"}) String comment) {
        bookService.addComment(bookId, comment);
    }

    @ShellMethod(key = "books-remove-comment", value = "Remove comment from book")
    public void removeBookComment(@ShellOption({"BookID"}) String bookId,
                                  @ShellOption({"CommentId"}) String commentId) {
        bookService.removeCommentFromBook(bookId, commentId);
    }

    @ShellMethod(key = "books-remove", value = "Remove book by id")
    public void removeBook(@ShellOption({"BookID"}) String bookId) {
        bookService.removeById(bookId);
    }

    @ShellMethod(key = "books-comment", value = "Show book comments by id book")
    public void getBookComments(@ShellOption({"BookID"}) String bookId) {
        bookService.getAllBookComment(bookId).forEach(comment -> inputOutputComponent.writeOutput(comment.toString()));
    }


}
