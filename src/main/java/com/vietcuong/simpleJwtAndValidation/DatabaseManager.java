package com.vietcuong.simpleJwtAndValidation;

import com.vietcuong.simpleJwtAndValidation.repository.ClientRepository;
import com.vietcuong.simpleJwtAndValidation.repository.authentication.TokenRepository;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {

    private final TokenRepository tokenRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public DatabaseManager(TokenRepository tokenRepository,
            ClientRepository clientRepository) {
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
