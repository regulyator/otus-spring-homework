package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.data.AuthorService;

import java.util.Collection;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistById(long id) {
        return authorRepository.existsById(id);
    }


    @Override
    @Transactional()
    public Author create(String newAuthorFio) {
        return authorRepository.save(new Author(0L, newAuthorFio));
    }

    @Override
    @Transactional
    public Author createOrUpdate(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional()
    public Author changeAuthorFio(long idAuthor, String newAuthorFio) {
        Author author = authorRepository.findById(idAuthor).orElseThrow(EntityNotFoundException::new);
        author.setFio(newAuthorFio);
        return authorRepository.save(author);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Author getById(long id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Collection<Author> getAll(List<Long> authorsIds) {
        return authorRepository.findAllByIdIn(authorsIds);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        authorRepository.deleteById(id);
    }
}
