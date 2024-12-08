package com.clients.api.rest.management.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Test
    void shouldGenerateValidToken() {
        String username = "testUser";
        String token = jwtTokenProvider.createToken(username);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
        assertEquals(username, jwtTokenProvider.getUsername(token));
    }

    @Test
    void shouldInvalidateTokenAfterTampering() {
        String username = "testUser";
        String token = jwtTokenProvider.createToken(username);
        String tamperedToken = token.substring(0, token.length() - 1) + "x";

        assertFalse(jwtTokenProvider.validateToken(tamperedToken));
    }
}

