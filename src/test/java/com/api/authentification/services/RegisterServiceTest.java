package com.api.authentification.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;
import com.api.authentification.mapper.AuthMapper;
import com.api.authentification.repositories.CompteRepository;

class RegisterServiceTest {

    private CompteRepository compteRepository;
    private AuthMapper authMapper;
    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        compteRepository = mock(CompteRepository.class);
        authMapper = new AuthMapper();
        registerService = new RegisterService(compteRepository, authMapper);
    }

    @Test
    void register_shouldCreateUser_whenDataIsValid() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPasswordHash("passwordhash");

        when(compteRepository.existsById("testuser")).thenReturn(false);
        when(compteRepository.save(any())).thenReturn(new Compte("testuser", "passwordhash"));

        UserDTO result = registerService.register(userDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(compteRepository, times(1)).save(any(Compte.class));
    }

    @Test
    void register_shouldThrowBadRequest_whenUsernameExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPasswordHash("passwordhash");

        when(compteRepository.existsById("testuser")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registerService.register(userDTO);
        });

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void register_shouldThrowBadRequest_whenDataMissing() {
        UserDTO userDTO = new UserDTO();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registerService.register(userDTO);
        });

        assertEquals(400, exception.getStatusCode().value());
    }
}
