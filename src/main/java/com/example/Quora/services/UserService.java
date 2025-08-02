package com.example.Quora.services;

import com.example.Quora.models.User;
import com.example.Quora.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isRegistered(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent();
    }

    public User createNewUser(User user) {
        User user1 = User.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .bio(user.getBio())
                .build();
        return user;
    }

    public User findUserById(UUID id) {
        if(userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        else
            return null;
    }

    public User updateUser(User user) { // Updates Registered users and create if not registered
        if(userRepository.findById(user.getId()).isPresent()) {
            User u = findUserById(user.getId());
            u.setId(user.getId());
            u.setUserName(user.getUserName());
            u.setEmail(user.getEmail());
            u.setBio(user.getBio());
            return u;
        }
        else {
            return createNewUser(user);
        }

    }

}
