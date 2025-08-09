package com.example.Quora.services;

import com.example.Quora.models.Question;

import com.example.Quora.models.Topic;
import com.example.Quora.repositories.QuestionRepository;
import com.example.Quora.repositories.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    public QuestionService (QuestionRepository questionRepository, TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
    }

    public Question createNewQuestion(Question question) {

        // Handle topic mapping (create if not exists)
        Set<Topic> finalTopics = new HashSet<>();
        for (Topic topic : question.getTopics()) {
            Topic existing = topicRepository.findByName(topic.getName())
                    .orElseGet(() -> topicRepository.save(topic));
            finalTopics.add(existing);
        }

        question.setTopics(finalTopics);
        return questionRepository.save(question);
    }


    public List<Question> findQuestionsByTextAndTopics(String text, Set<String> topicNames) {
        // Prepare the parameters for the repository
        // Normalize the text parameter for the LIKE clause
        String searchText = StringUtils.hasText(text) ? text : null;

        // Ensure topicNames is null if it's empty, so the query handles it correctly
        Set<String> searchTopics = (topicNames != null && !topicNames.isEmpty()) ? topicNames : null;

        // Call the custom repository method
        return questionRepository.findByTextAndTopics(searchText, searchTopics);
    }
}
