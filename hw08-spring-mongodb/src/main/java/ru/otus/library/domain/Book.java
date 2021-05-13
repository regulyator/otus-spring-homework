package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

}
