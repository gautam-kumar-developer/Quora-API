package com.example.Quora.services;

import com.example.Quora.models.Topic;
import com.example.Quora.repositories.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createNewTopic(Topic topic) {
        Topic t = Topic.builder()
                .name(topic.getName())
                .build();
        return topicRepository.save(t);
    }

    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }
}
