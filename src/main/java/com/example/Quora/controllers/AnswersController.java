package com.example.Quora.controllers;

import com.example.Quora.models.Answer;
import com.example.Quora.models.Question;
import com.example.Quora.models.User;
import com.example.Quora.services.AnswerService;
import com.example.Quora.services.QuestionService;
import com.example.Quora.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AnswersController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    public AnswersController(AnswerService answerService, QuestionService questionService, UserService userService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.userService = userService;
    }

    // this api is called only when the question is present
    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<?> postAnswer(@PathVariable UUID questionId, @RequestBody Answer answer) {
        Optional<Question> question = questionService.findQuestionById(questionId);
        User user = userService.findUserById(answer.getUser().getId());
        if (question.isPresent() && user != null) {
            answer.setQuestion(question.get());
            answer.setUser(user);
        } else {
            return new ResponseEntity<String>("Something Went Wrong...", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(answerService.createAnswer(answer), HttpStatus.CREATED);
    }

    @PutMapping("/answers/{answerId}")
    public ResponseEntity<?> putAnswer(@PathVariable UUID answerId, @RequestBody Answer answer) {
        Optional<Answer> answer1 = answerService.findAnswerById(answerId);
        if (answer1.isPresent()) {
            return new ResponseEntity<>(answerService.updateAnswer(answer1.get(), answer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No such answer with specified Id.", HttpStatus.NOT_FOUND);
        }
    }
}