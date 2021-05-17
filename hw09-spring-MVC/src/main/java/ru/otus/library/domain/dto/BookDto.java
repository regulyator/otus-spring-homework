package ru.otus.library.domain.dto;

import lombok.*;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
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
        this.authors = new ArrayList<>(book.getAuthors());
        this.comments = new ArrayList<>(book.getComments());
    }
}
