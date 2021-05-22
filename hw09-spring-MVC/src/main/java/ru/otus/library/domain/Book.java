package ru.otus.library.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.library.domain.dto.BookDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("Books")
public class Book {
    @EqualsAndHashCode.Include
    @Id
    private String id;

    @Field("book_name")
    private String bookName;

    @Field("genre")
    private Genre genre;

    @DBRef(lazy = true)
    private List<Author> authors = new ArrayList<>();

    @Field("comments")
    private List<Comment> comments = new ArrayList<>();

    public Book(@NonNull BookDto bookDto) {
        this.id = bookDto.getId();
        this.bookName = bookDto.getBookName();
        this.genre = bookDto.getGenre();
        this.authors = bookDto.getAuthors();
        this.comments = bookDto.getComments();
    }

}
