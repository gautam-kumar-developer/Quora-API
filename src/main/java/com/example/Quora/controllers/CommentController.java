package com.example.Quora.controllers;

import com.example.Quora.models.Answer;
import com.example.Quora.models.Comment;
import com.example.Quora.models.User;
import com.example.Quora.services.AnswerService;
import com.example.Quora.services.CommentService;
import com.example.Quora.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final AnswerService answerService;
    private final UserService userService;

    public CommentController(CommentService commentService, AnswerService answerService, UserService userService) {
        this.commentService = commentService;
        this.answerService = answerService;
        this.userService = userService;
    }

    // this method will only be when there is valid answer
    @PostMapping("/answers/{answerId}/comments")
    public ResponseEntity<?> commentOnAnswer(@PathVariable UUID answerId, @RequestBody Comment comment){
        Optional<Answer> answer = answerService.findAnswerById(answerId);
        User user = userService.findUserById(comment.getUser().getId());
        if(answer.isPresent() && user != null) {
            comment.setParentId(answer.get().getId());
            comment.setUser(user);
            return new ResponseEntity<>(commentService.createParentComment(comment), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something Went Wrong...", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/comments/{commentId}/comments")
    public ResponseEntity<?> commentOnComment(@PathVariable UUID commentId, @RequestBody Comment comment){
        Optional<Comment> comment1 = commentService.findCommentById(commentId);
        User user = userService.findUserById(comment.getUser().getId());
        if(comment1.isPresent() && user != null) {
            comment.setParentId(comment1.get().getId());
            comment.setUser(user);
            return new ResponseEntity<>(commentService.createParentComment(comment), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something Went Wrong...", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
