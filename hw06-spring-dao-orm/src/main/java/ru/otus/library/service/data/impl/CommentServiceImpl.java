package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.CommentDao;
import ru.otus.library.domain.Comment;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.service.data.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Comment getById(long id) {
        return commentDao.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public Comment updateCommentCaption(long id, String newCommentCaption) {
        Comment comment = commentDao.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setCaption(newCommentCaption);
        return commentDao.save(comment);
    }
}
