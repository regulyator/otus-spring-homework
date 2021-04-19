package ru.otus.library.service.data.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.CommentDto;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(String id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CommentDto getByIdDto(String id) {
        return new CommentDto(commentRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public Comment updateCommentCaption(String id, String newCommentCaption) {
        Comment comment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        comment.setCaption(newCommentCaption);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment addCommentToBook(String idBook, String newCommentCaption) {
        Book book = bookService.getById(idBook);
        return commentRepository.save(new Comment(null, newCommentCaption, book));
    }

    @Override
    @Transactional
    public void removeComment(String idComment) {
        commentRepository.deleteById(idComment);
    }
}
