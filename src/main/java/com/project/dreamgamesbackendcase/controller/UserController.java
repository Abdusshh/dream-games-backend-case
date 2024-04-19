package com.project.dreamgamesbackendcase.controller;

import com.project.dreamgamesbackendcase.entity.User;
import com.project.dreamgamesbackendcase.repository.UserRepository;
import com.project.dreamgamesbackendcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//  For debugging
    @GetMapping("/get-all-users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser() {
        User newUser = userService.createUser();
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update-level/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId) {
        User updatedUser = userService.updateUserLevel(userId, 1, 25);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
