package ru.otus.library.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.data.CommentService;
import ru.otus.library.service.util.InputOutputComponent;

@ShellComponent
@ShellCommandGroup("Comment commands")
public class LibraryAppShellComment {
    private final InputOutputComponent inputOutputComponent;
    private final CommentService commentService;

    @Autowired
    public LibraryAppShellComment(InputOutputComponent inputOutputComponent,
                                  CommentService commentService) {
        this.inputOutputComponent = inputOutputComponent;
        this.commentService = commentService;
    }

    /*@ShellMethod(key = "comments", value = "Show comment by id")
    public void getComment(@ShellOption({"CommentID"}) long commentId) {
        inputOutputComponent.writeOutput(commentService.getByIdDto(commentId).toString());
    }

    @ShellMethod(key = "comments-all-book", value = "Show all book comments")
    public void getAllComments(@ShellOption({"BookID"}) long bookId) {
        commentService.getAllBookCommentDto(bookId)
                .forEach(commentDto -> inputOutputComponent.writeOutput(commentDto.toString()));
    }


    @ShellMethod(key = "comments-add", value = "Add comment to book")
    public void addBookComment(@ShellOption({"BookID"}) long bookId,
                               @ShellOption({"Comment"}) String newComment) {
        commentService.addCommentToBook(bookId, newComment);
    }

    @ShellMethod(key = "comments-remove", value = "Remove comment")
    public void removeBookComment(@ShellOption({"CommentId"}) long commentId) {
        commentService.removeComment(commentId);
    }*/


}