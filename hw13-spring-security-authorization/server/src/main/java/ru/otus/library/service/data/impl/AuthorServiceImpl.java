package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
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
    public Author createOrUpdate(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    public Collection<Author> getAll() {
        return authorRepository.findAll();
    }


    @Override
    public void removeById(String id) {
        authorRepository.deleteById(id);
    }
}
