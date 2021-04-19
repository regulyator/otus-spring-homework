package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public boolean checkExistById(String id) {
        return genreRepository.existsById(id);
    }

    @Override
    @Transactional()
    public Genre create(String newGenreCaption) {
        return genreRepository.save(new Genre(null, newGenreCaption));
    }

    @Override
    @Transactional
    public Genre createOrUpdate(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional()
    public Genre changeGenreCaption(String idGenre, String newGenreCaption) {
        Genre genre = genreRepository.findById(idGenre).orElseThrow(EntityNotFoundException::new);
        genre.setCaption(newGenreCaption);
        return genreRepository.save(genre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public void removeById(String id) {
        genreRepository.deleteById(id);
    }
}
