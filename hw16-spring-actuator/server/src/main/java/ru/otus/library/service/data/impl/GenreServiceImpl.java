package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.data.GenreService;
import ru.otus.library.service.security.AclPermissionGrantService;

import java.util.Collection;
import java.util.Objects;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final AclPermissionGrantService aclPermissionGrantservice;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, AclPermissionGrantService aclPermissionGrantservice) {
        this.genreRepository = genreRepository;
        this.aclPermissionGrantservice = aclPermissionGrantservice;
    }

    @Override
    public boolean checkExistById(String id) {
        return genreRepository.existsById(id);
    }

    @Override
    public Genre createOrUpdate(Genre genre) {
        final boolean isNewEntity = Objects.isNull(genre.getId()) || genre.getId().isEmpty();

        if (isNewEntity) {
            final Genre savedGenre = genreRepository.save(genre);
            aclPermissionGrantservice.grantAclPermission(savedGenre);
            return savedGenre;
        } else {
            return genreRepository.save(genre);
        }
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasRole('ROLE_ADMIN')")
    public Collection<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public void removeById(String id) {
        genreRepository.deleteById(id);
    }
}
