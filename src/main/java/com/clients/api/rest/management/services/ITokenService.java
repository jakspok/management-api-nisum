package com.clients.api.rest.management.services;


import com.clients.api.rest.management.model.UserToken;

import java.time.LocalDateTime;

public interface ITokenService {

    void saveToken(String username, String token, LocalDateTime expirationDate);

    UserToken getToken(String token);

    void deleteToken(String token);
}
