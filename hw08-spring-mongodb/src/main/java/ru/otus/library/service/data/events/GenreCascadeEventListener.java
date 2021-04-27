package ru.otus.library.service.data.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.repository.BookRepository;

import java.util.List;
import java.util.Objects;

@Component
public class GenreCascadeEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Autowired
    public GenreCascadeEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        String genreId = event.getSource().getObjectId("_id").toString();
        if (bookRepository.existsBookByGenre_Id(genreId)) {
            throw new ReferenceEntityException("Can't delete! Genre has reference Book.");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Genre> event) {
        if (Objects.requireNonNull(event.getDocument()).containsKey("_id")) {
            final Genre updatedGenre = event.getSource();
            List<Book> booksContainsGenre = bookRepository.findAllByGenre_Id(updatedGenre.getId());
            booksContainsGenre
                    .stream()
                    .filter(book -> !book.getGenre().getCaption().equals(updatedGenre.getCaption()))
                    .map(book -> {
                        book.setGenre(updatedGenre);
                        return book;
                    })
                    .forEach(bookRepository::save);
        }
    }
}
