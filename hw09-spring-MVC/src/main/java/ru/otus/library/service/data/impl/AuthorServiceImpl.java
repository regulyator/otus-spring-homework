package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public boolean checkExistById(String id) {
        return authorRepository.existsById(id);
    }


    @Override
    public Author create(String newAuthorFio) {
        return authorRepository.save(new Author(null, newAuthorFio));
    }

    @Override
    public Author createOrUpdate(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author changeAuthorFio(String idAuthor, String newAuthorFio) {
        Author author = authorRepository.findById(idAuthor).orElseThrow(EntityNotFoundException::new);
        author.setFio(newAuthorFio);
        return authorRepository.save(author);
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Collection<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> getAll(Collection<String> authorsIds) {
        return authorRepository.findAllByIdIn(authorsIds);
    }

    @Override
    public void removeById(String id) {
        authorRepository.deleteById(id);
    }
}
