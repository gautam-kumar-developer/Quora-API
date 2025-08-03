package com.example.Quora.controllers;

import com.example.Quora.models.User;
import com.example.Quora.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        if (userService.isRegistered(user.getUserName())) {
            return new ResponseEntity<String>("UserName Already Exists", HttpStatus.OK);
        } else {
            User u = userService.createNewUser(user);
            return new ResponseEntity<User>(u, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable UUID userId) {
        User user = userService.findUserById(userId);
        if (user != null)
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<String>("No such user found with specified Id.", HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<?> getAllUser() {
        List<User> userList = userService.finAllUser();
            return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> putUser(@RequestBody User user, @PathVariable UUID userId) {
        User u = userService.findUserById(userId);
        if (u != null)
            return new ResponseEntity<User>(userService.updateUser(userId, user), HttpStatus.OK);
        else
            return new ResponseEntity<String>("No such user found with specified details.", HttpStatus.NOT_FOUND);
    }

}
