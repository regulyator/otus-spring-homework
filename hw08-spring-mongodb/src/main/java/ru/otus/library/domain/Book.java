package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Author> authors = new HashSet<>();

}
