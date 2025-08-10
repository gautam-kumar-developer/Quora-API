package com.example.Quora.services;

import com.example.Quora.models.Answer;
import com.example.Quora.models.Question;
import com.example.Quora.models.User;
import com.example.Quora.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public UserService(UserRepository userRepository, QuestionService questionService, AnswerService answerService) {
        this.userRepository = userRepository;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    public void run(String... args) {
        // Dummy User Data
        User u1 = User.builder()
                .userName("ronnySharam")
                .email("xyz@gmail.com")
                .build();
        userRepository.save(u1);
        System.out.println("User Id1 : " + u1.getId());

        User u2 = User.builder()
                .userName("rohitSharam")
                .email("abc@gmail.com")
                .build();
        userRepository.save(u2);
        System.out.println("User Id2 : " + u2.getId());

        // creating dummy question
        Question question = questionService.createDummyQuestion(u1);

        // creating dummy answer for the above question
        Answer answer = answerService.createDummyAnswer(question, u2);
    }

    public boolean isRegistered(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.isPresent();
    }

    public User createNewUser(User user) {
        User user1 = User.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .bio(user.getBio())
                .questions(user.getQuestions())
                .build();
        userRepository.save(user1);
        return user1;
    }

    public User findUserById(UUID id) {
        if (userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        else
            return null;
    }

    public User updateUser(UUID userId, User user) { // Updates Registered users only
        User u = findUserById(userId);
        if(user.getUserName() != null && !user.getUserName().equals(u.getUserName())) {
            u.setUserName(user.getUserName());
        }
        if(user.getEmail() != null && !user.getEmail().equals(u.getEmail())) {
            u.setEmail(user.getEmail());
        }
        if(user.getBio() != null && !user.getBio().equals(u.getBio())) {
            u.setBio(user.getBio());
        }

        userRepository.save(u);
        return u;

    }

    public List<User> finAllUser() {
        return userRepository.findAll();
    }
}
