package ru.otus.library.domain.dto;

import lombok.*;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class BookDto {
    private String id;
    private String bookName;
    private Genre genre;
    private List<Author> authors;
    private List<Comment> comments;

    public BookDto(@NonNull Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.genre = book.getGenre();
        this.authors = new ArrayList<>(Objects.nonNull(book.getAuthors()) ? book.getAuthors() : new ArrayList<>());
        this.comments = new ArrayList<>(Objects.nonNull(book.getComments()) ? book.getComments() : new ArrayList<>());
    }
}
