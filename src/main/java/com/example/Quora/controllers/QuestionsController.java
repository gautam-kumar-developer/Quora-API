package com.example.Quora.controllers;

import com.example.Quora.models.Question;
import com.example.Quora.models.User;
import com.example.Quora.services.QuestionService;
import com.example.Quora.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

    private final QuestionService questionService;
    private final UserService userService;

    public QuestionsController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {
        // Validate and fetch user
        UUID userId = question.getUser().getId();
        User user = userService.findUserById(userId);
        if(user != null)
            question.setUser(user);
        else
            return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<Question>(questionService.createNewQuestion(question), HttpStatus.CREATED);

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchQuestions(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "tags", required = false) Set<String> topicNames) {

        // Call the service to perform the search
        List<Question> questions = questionService.findQuestionsByTextAndTopics(text, topicNames);

        if (questions.isEmpty()) {
            return new ResponseEntity<String>("No Match Found for the given Search.",HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(questions);
    }
}

