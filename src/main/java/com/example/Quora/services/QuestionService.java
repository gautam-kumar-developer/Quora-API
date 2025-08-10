package com.example.Quora.services;

import com.example.Quora.models.Question;

import com.example.Quora.models.Topic;
import com.example.Quora.models.User;
import com.example.Quora.repositories.QuestionRepository;
import com.example.Quora.repositories.TopicRepository;
import com.example.Quora.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.*;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public QuestionService (QuestionRepository questionRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
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

    public Optional<Question> findQuestionById(UUID questionId) {
        return questionRepository.findById(questionId);
    }

    public Question createDummyQuestion(User u1) {
        Topic t1 = Topic.builder()
                .name("Java")
                .build();
        topicRepository.save(t1);
        Topic t2 = Topic.builder()
                .name("OOP")
                .build();
        topicRepository.save(t2);

        Set<Topic> topics = new HashSet<>();
        topics.add(t1);
        topics.add(t2);
        Question question = Question.builder()
                .title("What is polymorphism in Java?")
                .body("Can someone explain this concept with examples?")
                .user(userRepository.findById(u1.getId()).orElse(null))
                .topics(topics)
                .build();
        Question q = questionRepository.save(question);
        System.out.println("Question Id : " + q.getId());
        return q;
    }
}
