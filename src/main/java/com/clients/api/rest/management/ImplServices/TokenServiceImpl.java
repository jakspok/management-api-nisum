package com.clients.api.rest.management.ImplServices;

import com.clients.api.rest.management.model.UserToken;
import com.clients.api.rest.management.repository.UserTokenRepository;
import com.clients.api.rest.management.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements ITokenService {

    private final UserTokenRepository userTokenRepository;

    @Autowired
    public TokenServiceImpl (UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public void saveToken(String username, String token, LocalDateTime expirationDate) {
        UserToken userToken = UserToken.builder()
                .username(username)
                .token(token)
                .expirationDate(expirationDate)
                .build();
        userTokenRepository.save(userToken);
    }

    public UserToken getToken(String username) {
        return userTokenRepository.findByUsername(username);
    }

    public void deleteToken(String token) {
        userTokenRepository.deleteByToken(token);
    }
}

