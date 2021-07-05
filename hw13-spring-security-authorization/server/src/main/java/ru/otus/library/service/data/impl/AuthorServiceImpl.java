package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.security.AclPermissionGrant;

import java.util.Collection;
import java.util.Objects;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AclPermissionGrant aclPermissionGrant;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AclPermissionGrant aclPermissionGrant) {
        this.authorRepository = authorRepository;
        this.aclPermissionGrant = aclPermissionGrant;
    }

    @Override
    public boolean checkExistById(String id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author createOrUpdate(Author author) {
        final boolean isNewEntity = Objects.isNull(author.getId()) || author.getId().isEmpty();

        if (isNewEntity) {
            final Author savedAuthor = authorRepository.save(author);
            aclPermissionGrant.grantAclPermission(savedAuthor);
            return savedAuthor;
        } else {
            return authorRepository.save(author);
        }
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'ADMINISTRATION') or hasRole('ROLE_ADMIN')")
    public Collection<Author> getAll() {
        return authorRepository.findAll();
    }


    @Override
    public void removeById(String id) {
        authorRepository.deleteById(id);
    }
}
