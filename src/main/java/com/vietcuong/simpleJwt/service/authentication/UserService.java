package com.vietcuong.simpleJwt.service.authentication;

import com.vietcuong.simpleJwt.config.SecurityConfig;
import com.vietcuong.simpleJwt.entity.authentication.User;
import com.vietcuong.simpleJwt.repository.authentication.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final SecurityConfig securityConfig;

    public UserService(UserRepository userRepository,
            UserDetailsServiceImpl userDetailsService,
            SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.securityConfig = securityConfig;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<String> allUsersToString() {
        List<String> finalList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            finalList.add(user.toString());
        }
        return finalList;
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkPassword(String username, String rawPassword) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        return securityConfig.passwordEncoder()
                .matches(rawPassword, user.getPassword());
    }
}
