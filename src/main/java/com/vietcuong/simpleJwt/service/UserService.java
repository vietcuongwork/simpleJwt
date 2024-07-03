package com.vietcuong.simpleJwt.service;

import com.vietcuong.simpleJwt.config.SecurityConfig;
import com.vietcuong.simpleJwt.entity.User;
import com.vietcuong.simpleJwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    // UserRepository dependency injection to interact with user data in the database
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final SecurityConfig securityConfig;

    // Constructor to initialize UserRepository
    public UserService(UserRepository userRepository, UserDetailsServiceImpl userDetailsService,
                       SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.securityConfig = securityConfig;
    }

    // Method to retrieve all users from the database
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // Method to retrieve all users and convert them to a list of string representations
    public List<String> allUsersToString() {
        List<String> finalList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            finalList.add(user.toString()); // Convert each user object to a string and add to finalList
        }
        return finalList;
    }

    // Method to delete a user by their ID
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkPassword(String username, String rawPassword) {
        // Load the user details from the UserDetailsService by username
        User user = (User) userDetailsService.loadUserByUsername(username);

        // Compare the raw password provided by the client with the stored hashed password
        // using the password encoder configured in securityConfig
        return securityConfig.passwordEncoder().matches(rawPassword, user.getPassword());
    }
}
