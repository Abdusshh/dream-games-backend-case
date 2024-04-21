package com.project.dreamgamesbackendcase.service;

import com.project.dreamgamesbackendcase.entity.User;
import com.project.dreamgamesbackendcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser() {
        User newUser = new User();

        newUser.setLevel(1);
        newUser.setCoins(5000);

        return userRepository.save(newUser);
    }

    public User updateUserLevel(Long userId, int levelIncrement, int coinIncrement) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLevel(user.getLevel() + levelIncrement);
        user.setCoins(user.getCoins() + coinIncrement);

        return userRepository.save(user);
    }
}
