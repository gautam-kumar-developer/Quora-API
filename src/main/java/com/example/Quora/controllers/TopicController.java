package com.example.Quora.controllers;

import com.example.Quora.models.Topic;
import com.example.Quora.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        return new ResponseEntity<>(topicService.createNewTopic(topic), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics(){
        return new ResponseEntity<>(topicService.findAllTopics(), HttpStatus.OK);
    }

}
