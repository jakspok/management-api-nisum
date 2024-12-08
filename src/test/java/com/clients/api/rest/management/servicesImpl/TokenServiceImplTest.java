package com.clients.api.rest.management.servicesImpl;

import com.clients.api.rest.management.ImplServices.TokenServiceImpl;
import com.clients.api.rest.management.model.UserToken;
import com.clients.api.rest.management.repository.UserTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {

    @Mock
    private UserTokenRepository userTokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveToken() {
        // Arrange
        String username = "testUser";
        String token = "sampleToken";
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(1);

        UserToken userToken = UserToken.builder()
                .username(username)
                .token(token)
                .expirationDate(expirationDate)
                .build();

        // Act
        tokenServiceImpl.saveToken(username, token, expirationDate);

        // Assert
        verify(userTokenRepository, times(1)).save(userToken);
    }

    @Test
    void testGetToken_Success() {
        // Arrange
        String username = "testUser";
        UserToken expectedToken = UserToken.builder()
                .username(username)
                .token("sampleToken")
                .expirationDate(LocalDateTime.now().plusHours(1))
                .build();

        when(userTokenRepository.findByUsername(username)).thenReturn(expectedToken);

        // Act
        UserToken actualToken = tokenServiceImpl.getToken(username);

        // Assert
        assertNotNull(actualToken);
        assertEquals(expectedToken, actualToken);
        verify(userTokenRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetToken_NotFound() {
        // Arrange
        String username = "nonExistentUser";

        when(userTokenRepository.findByUsername(username)).thenReturn(null);

        // Act
        UserToken result = tokenServiceImpl.getToken(username);

        // Assert
        assertNull(result);
        verify(userTokenRepository, times(1)).findByUsername(username);
    }

    @Test
    void testDeleteToken() {
        // Arrange
        String token = "sampleToken";

        // Act
        tokenServiceImpl.deleteToken(token);

        // Assert
        verify(userTokenRepository, times(1)).deleteByToken(token);
    }
}
