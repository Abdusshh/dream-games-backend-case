package com.project.dreamgamesbackendcase.service;

import com.project.dreamgamesbackendcase.entity.User;
import com.project.dreamgamesbackendcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    // Creates a new user with a default level of 1 and a starting balance of 5000 coins.
    public User createUser() {
        User newUser = new User();  // instantiate a new User object

        // Set initial user properties
        newUser.setLevel(1);        // default starting level
        newUser.setCoins(5000);     // default starting coin balance

        return userRepository.save(newUser);  // save the new user to the database and return it
    }

    // Updates the level and coin balance of an existing user.
    public User updateUserLevel(Long userId, int levelIncrement, int coinIncrement) {
        // Retrieve the user by ID or throw an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user's level and coin balance
        user.setLevel(user.getLevel() + levelIncrement);
        user.setCoins(user.getCoins() + coinIncrement);

        // Save the updated user to the database and return it
        return userRepository.save(user);
    }
}
