package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.GenreDao;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.EntityNotFoundException;
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
    @Transactional(readOnly = true)
    public boolean checkExistById(long id) {
        return genreDao.isExistById(id);
    }

    @Override
    @Transactional()
    public Genre create(String newGenreCaption) {
        return genreDao.save(new Genre(0L, newGenreCaption));
    }

    @Override
    @Transactional
    public Genre createOrUpdate(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    @Transactional()
    public Genre changeGenreCaption(long idGenre, String newGenreCaption) {
        Genre genre = genreDao.findById(idGenre).orElseThrow(EntityNotFoundException::new);
        genre.setCaption(newGenreCaption);
        return genreDao.save(genre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Genre getById(long id) {
        return genreDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Genre> getAll() {
        return genreDao.findAll();
    }

    @Override
    @Transactional
    public void removeById(long id) {
        genreDao.deleteById(id);
    }
}
