package com.clients.api.rest.management.controller;

import com.clients.api.rest.management.ImplServices.TokenServiceImpl;
import com.clients.api.rest.management.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenServiceImpl tokenServiceImpl;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, TokenServiceImpl tokenServiceImpl) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenServiceImpl = tokenServiceImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username) {

        String token = jwtTokenProvider.createToken(username);

        tokenServiceImpl.saveToken(username, token, LocalDateTime.now ());
        return ResponseEntity.ok(token);
    }
}

