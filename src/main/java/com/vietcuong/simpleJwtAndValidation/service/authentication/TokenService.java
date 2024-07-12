package com.vietcuong.simpleJwtAndValidation.service.authentication;

import com.vietcuong.simpleJwtAndValidation.entity.authentication.Token;
import com.vietcuong.simpleJwtAndValidation.repository.authentication.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<Token> getAllToken() {
        return tokenRepository.findAll();
    }

    public List<String> allTokensToString() {
        List<String> finalList = new ArrayList<>();
        List<Token> tokenList = tokenRepository.findAll();
        for (Token token : tokenList) {
            finalList.add(token.toString());
        }
        return finalList;
    }
}
