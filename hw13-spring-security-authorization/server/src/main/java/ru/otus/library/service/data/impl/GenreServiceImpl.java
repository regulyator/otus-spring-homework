package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public boolean checkExistById(String id) {
        return genreRepository.existsById(id);
    }

    @Override
    public Genre createOrUpdate(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    public Collection<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public void removeById(String id) {
        genreRepository.deleteById(id);
    }
}
