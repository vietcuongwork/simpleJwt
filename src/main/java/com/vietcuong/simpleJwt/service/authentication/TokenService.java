package com.vietcuong.simpleJwt.service.authentication;

import com.vietcuong.simpleJwt.entity.Token;
import com.vietcuong.simpleJwt.repository.TokenRepository;
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
