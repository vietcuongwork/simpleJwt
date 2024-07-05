package com.vietcuong.simpleJwt.repository.authentication;

import com.vietcuong.simpleJwt.entity.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Repository interface for User entity, extending JpaRepository to provide CRUD operations
public interface UserRepository extends JpaRepository<User, Integer> {
    // Method to find a User by username, returning an Optional to handle possible null values
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    void deleteByUsername(String username);
}