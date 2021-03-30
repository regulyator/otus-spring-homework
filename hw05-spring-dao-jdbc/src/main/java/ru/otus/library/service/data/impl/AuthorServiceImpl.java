package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;
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
    public boolean checkExistById(long id) {
        return authorDao.isExistById(id);
    }


    @Override
    public Author create(String newAuthorFio) {
        return create(new Author(0L, newAuthorFio));
    }

    @Override
    public void update(Author author) {
        authorDao.update(author);
    }

    @Override
    public Author getById(long id) {
        return authorDao.findById(id);
    }

    @Override
    public Collection<Author> getAll() {
        return authorDao.findAll();
    }

    @Override
    public void removeById(long id) {
        authorDao.deleteById(id);
    }

    private Author create(Author author) {
        final long generatedId = authorDao.insert(author);
        author.setId(generatedId);
        return author;
    }
}
