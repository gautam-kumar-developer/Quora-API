package com.example.Quora.controllers;

import com.example.Quora.models.User;
import com.example.Quora.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    public UsersController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){

        if(userService.isRegistered(user.getId())){
            return new ResponseEntity<>("User Already Exists", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.createNewUser(user), HttpStatus.CREATED);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable UUID userId){
        User user = userService.findUserById(userId);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("No such user found with specified Id.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> putUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

}
