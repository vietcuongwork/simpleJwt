package com.vietcuong.simpleJwt;

import com.vietcuong.simpleJwt.repository.ClientRepository;
import com.vietcuong.simpleJwt.repository.authentication.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class DatabaseManager {

    private final TokenRepository tokenRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public DatabaseManager(TokenRepository tokenRepository, ClientRepository clientRepository) {
        this.tokenRepository = tokenRepository;
        this.clientRepository = clientRepository;
    }

    @PreDestroy
    public void clearTokenTable() {
        System.out.println("Clearing tokens table before shutdown...");
        tokenRepository.deleteAll();
    }

    @PreDestroy
    public void clearClientTable() {
        clientRepository.deleteAll();
    }
}
