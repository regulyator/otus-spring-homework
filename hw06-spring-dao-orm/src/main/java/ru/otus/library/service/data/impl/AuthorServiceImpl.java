package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.service.data.AuthorService;

import java.util.Collection;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkExistById(long id) {
        return authorDao.isExistById(id);
    }


    @Override
    @Transactional()
    public Author create(String newAuthorFio) {
        return authorDao.save(new Author(0L, newAuthorFio));
    }

    @Override
    @Transactional
    public Author createOrUpdate(Author author) {
        return authorDao.save(author);
    }

    @Override
    @Transactional()
    public Author changeAuthorFio(long idAuthor, String newAuthorFio) {
        Author author = authorDao.findById(idAuthor).orElseThrow(EntityNotFoundException::new);
        author.setFio(newAuthorFio);
        return authorDao.save(author);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Author getById(long id) {
        return authorDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Author> getAll() {
        return authorDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Collection<Author> getAll(Collection<Long> authorsIds) {
        return authorDao.findAll(authorsIds);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        authorDao.deleteById(id);
    }
}
