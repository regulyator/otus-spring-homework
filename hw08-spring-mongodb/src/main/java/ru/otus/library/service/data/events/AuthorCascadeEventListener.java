package ru.otus.library.service.data.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.util.Objects;

@Component
public class AuthorCascadeEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorCascadeEventListener(BookRepository bookRepository,
                                      AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        Author deletedAuthor = authorRepository.findById(event.getSource().getObjectId("_id").toString())
                .orElse(null);
        if (Objects.nonNull(deletedAuthor) && bookRepository.existsBookByAuthorsContaining(deletedAuthor)) {
            throw new ReferenceEntityException("Can't delete! Author has reference Book.");
        }
    }
}
