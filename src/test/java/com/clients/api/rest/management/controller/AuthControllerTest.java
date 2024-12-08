package com.clients.api.rest.management.controller;

import com.clients.api.rest.management.ImplServices.TokenServiceImpl;
import com.clients.api.rest.management.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private TokenServiceImpl tokenServiceImpl;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        String username = "testUser";
        String mockToken = "mockJwtToken";
        when(jwtTokenProvider.createToken(username)).thenReturn(mockToken);

        // Act
        ResponseEntity<?> response = authController.login(username);

        // Assert
        verify(jwtTokenProvider, times(1)).createToken(username);
        verify(tokenServiceImpl, times(1)).saveToken(eq(username), eq(mockToken), any(LocalDateTime.class));
        assertEquals(ResponseEntity.ok(mockToken), response);
    }

    @Test
    void testLoginError() {
        // Arrange
        String username = "testUser";
        when(jwtTokenProvider.createToken(username)).thenThrow(new RuntimeException("Error generating token"));

        // Act & Assert
        RuntimeException exception = null;
        try {
            authController.login(username);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertEquals("Error generating token", exception.getMessage());
        verify(jwtTokenProvider, times(1)).createToken(username);
        verify(tokenServiceImpl, never()).saveToken(anyString(), anyString(), any(LocalDateTime.class));
    }
}

