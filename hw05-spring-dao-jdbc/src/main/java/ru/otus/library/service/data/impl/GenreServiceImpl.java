package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.data.GenreService;

import java.util.Collection;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public boolean checkExistById(long id) {
        return genreDao.isExistById(id);
    }

    @Override
    public Genre create(String newGenreCaption) {
        return create(new Genre(0L, newGenreCaption));
    }

    @Override
    public void update(Genre genre) {
        genreDao.update(genre);
    }

    @Override
    public Genre getById(long id) {
        return genreDao.findById(id);
    }

    @Override
    public Collection<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    public void removeById(long id) {
        genreDao.deleteById(id);
    }

    private Genre create(Genre genre) {
        final long generatedId = genreDao.insert(genre);
        genre.setId(generatedId);
        return genre;
    }
}
