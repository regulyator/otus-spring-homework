package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.CommentDao;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.CommentDto;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.CommentService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final BookService bookService;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, BookService bookService) {
        this.commentDao = commentDao;
        this.bookService = bookService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Comment getById(long id) {
        return commentDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CommentDto getByIdDto(long id) {
        return new CommentDto(commentDao.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public Comment updateCommentCaption(long id, String newCommentCaption) {
        Comment comment = commentDao.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setCaption(newCommentCaption);
        return commentDao.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Comment> getAllBookComment(long bookId) {
        return commentDao.findAllByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CommentDto> getAllBookCommentDto(long bookId) {
        return commentDao.findAllByBookId(bookId).stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Comment addCommentToBook(long idBook, String newCommentCaption) {
        Book book = bookService.getById(idBook);
        return commentDao.save(new Comment(0L, newCommentCaption, book));
    }

    @Override
    @Transactional
    public void removeComment(long idComment) {
        commentDao.deleteById(idComment);
    }
}
