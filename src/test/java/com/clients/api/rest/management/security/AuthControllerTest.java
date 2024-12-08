package com.clients.api.rest.management.security;

import com.clients.api.rest.management.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldReturnTokenOnValidLogin() throws Exception {
        String username = "testUser";
        String token = "mockToken";

        Mockito.when(jwtTokenProvider.createToken(username)).thenReturn(token);

        mockMvc.perform(post("/auth/login").param("username", username))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }
}

