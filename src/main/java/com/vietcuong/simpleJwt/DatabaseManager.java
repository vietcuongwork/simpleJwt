package com.vietcuong.simpleJwt;

import com.vietcuong.simpleJwt.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class DatabaseManager {

    private final TokenRepository tokenRepository;

    @Autowired
    public DatabaseManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @PreDestroy
    public void clearTokensTable() {
        System.out.println("Clearing tokens table before shutdown...");
        tokenRepository.deleteAll();
    }
}
