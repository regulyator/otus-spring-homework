package ru.otus.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@ToString
public class BookDto {
    private final long id;
    private final String bookName;
    private final Genre genre;
    private final Set<Author> authors;
    private final Set<Comment> comments;

    public BookDto(@NonNull Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.genre = book.getGenre();
        this.authors = new HashSet<>(book.getAuthors());
        this.comments = new HashSet<>(book.getComments());
    }
}
