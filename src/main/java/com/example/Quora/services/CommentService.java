package com.example.Quora.services;

import com.example.Quora.models.Comment;
import com.example.Quora.models.User;
import com.example.Quora.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Comment createParentComment(Comment comment) {
        Comment c = Comment.builder()
                .parentId(comment.getParentId())
                .user(userService.findUserById(comment.getUser().getId()))
                .text(comment.getText())
                .build();

        return commentRepository.save(c);
    }

    public Optional<Comment> findCommentById(UUID commentId) {
        return commentRepository.findById(commentId);
    }
}
