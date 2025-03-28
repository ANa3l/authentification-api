package com.api.authentification.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.authentification.config.JwtUtil;
import com.api.authentification.dto.TokenDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;
import com.api.authentification.mapper.AuthMapper;
import com.api.authentification.repositories.CompteRepository;

/**
 * Test unitaire pour le service LoginService.
 * Vérifie que l'authentification renvoie un token JWT valide pour un utilisateur existant.
 */
class LoginServiceTest {

    private JwtUtil jwtUtil;
    private CompteRepository compteRepository;
    private AuthMapper authMapper;
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        compteRepository = mock(CompteRepository.class);
        authMapper = new AuthMapper();

        loginService = new LoginService(jwtUtil, compteRepository, authMapper);
    }

    @Test
    void authenticate_shouldReturnTokenDTO_whenCredentialsAreValid() {
        UserDTO request = new UserDTO();
        request.setUsername("user1");
        request.setPasswordHash("secret");

        Compte compte = new Compte("user1", "secret");

        when(compteRepository.findById("user1")).thenReturn(Optional.of(compte));
        when(jwtUtil.generateToken("user1")).thenReturn("faketoken");

        TokenDTO result = loginService.authenticate(request);

        assertNotNull(result);
        assertEquals("faketoken", result.getToken());
    }
}
