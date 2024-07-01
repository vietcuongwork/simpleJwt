package com.vietcuong.simpleJwt.service;

import com.vietcuong.simpleJwt.entity.User;
import com.vietcuong.simpleJwt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    // UserRepository dependency injection to interact with user data in the database
    private final UserRepository userRepository;

    // Constructor to initialize UserRepository
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
