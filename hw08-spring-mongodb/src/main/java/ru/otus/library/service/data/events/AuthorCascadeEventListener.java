package ru.otus.library.service.data.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.repository.BookRepository;

@Component
public class AuthorCascadeEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Autowired
    public AuthorCascadeEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        String deletedAuthorId = event.getSource().getObjectId("_id").toString();
        if (bookRepository.existsBookByAuthors_Id(deletedAuthorId)) {
            throw new ReferenceEntityException("Can't delete! Author has reference Book.");
        }
    }
}
