package com.api.authentification.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import com.api.authentification.config.JwtUtil;
import com.api.authentification.dto.ChangePasswordPayloadDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;
import com.api.authentification.mapper.AuthMapper;
import com.api.authentification.repositories.CompteRepository;

/**
 * Tests unitaires pour UpdatePasswordService.
 * Vérifie les différents cas de mise à jour de mot de passe (réussite, erreurs 403, 404, 400).
 */
class UpdatePasswordServiceTest {

    private CompteRepository compteRepository;
    private JwtUtil jwtUtil;
    private AuthMapper authMapper;
    private UpdatePasswordService updatePasswordService;

    @BeforeEach
    void setUp() {
        compteRepository = mock(CompteRepository.class);
        jwtUtil = mock(JwtUtil.class);
        authMapper = new AuthMapper();
        updatePasswordService = new UpdatePasswordService(compteRepository, jwtUtil, authMapper);
    }

    @Test
    void updatePassword_shouldUpdate_whenDataIsValid() {
        String token = "Bearer validtoken";
        String jwt = "validtoken";
        ChangePasswordPayloadDTO payload = new ChangePasswordPayloadDTO();
        payload.setCurrentPassword("oldPassword");
        payload.setNewPassword("newPassword");

        Compte compte = new Compte("testuser", "oldPassword");

        when(jwtUtil.extractIdentifiant(jwt)).thenReturn("testuser");
        when(compteRepository.findById("testuser")).thenReturn(Optional.of(compte));
        when(compteRepository.save(any())).thenReturn(new Compte("testuser", "newPassword"));

        UserDTO result = updatePasswordService.updatePassword(token, payload);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("newPassword", result.getPasswordHash());
    }

    @Test
    void updatePassword_shouldThrowForbidden_whenOldPasswordIncorrect() {
        String token = "Bearer validtoken";
        String jwt = "validtoken";
        ChangePasswordPayloadDTO payload = new ChangePasswordPayloadDTO();
        payload.setCurrentPassword("wrongPassword");
        payload.setNewPassword("newPassword");

        Compte compte = new Compte("testuser", "oldPassword");

        when(jwtUtil.extractIdentifiant(jwt)).thenReturn("testuser");
        when(compteRepository.findById("testuser")).thenReturn(Optional.of(compte));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            updatePasswordService.updatePassword(token, payload);
        });

        assertEquals(403, exception.getStatusCode().value());
    }

    @Test
    void updatePassword_shouldThrowNotFound_whenUserNotExists() {
        String token = "Bearer validtoken";
        String jwt = "validtoken";
        ChangePasswordPayloadDTO payload = new ChangePasswordPayloadDTO();
        payload.setCurrentPassword("oldPassword");
        payload.setNewPassword("newPassword");

        when(jwtUtil.extractIdentifiant(jwt)).thenReturn("nonexistent");
        when(compteRepository.findById("nonexistent")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            updatePasswordService.updatePassword(token, payload);
        });

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void updatePassword_shouldThrowBadRequest_whenMissingFields() {
        String token = "Bearer validtoken";
        String jwt = "validtoken";

        when(jwtUtil.extractIdentifiant(jwt)).thenReturn("testuser");

        Compte compte = new Compte("testuser", "oldPassword");
        when(compteRepository.findById("testuser")).thenReturn(Optional.of(compte));

        ChangePasswordPayloadDTO payload = new ChangePasswordPayloadDTO();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            updatePasswordService.updatePassword(token, payload);
        });

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void updatePassword_shouldThrowForbidden_whenTokenInvalid() {
        String token = "InvalidToken";
        ChangePasswordPayloadDTO payload = new ChangePasswordPayloadDTO();
        payload.setCurrentPassword("oldPassword");
        payload.setNewPassword("newPassword");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            updatePasswordService.updatePassword(token, payload);
        });

        assertEquals(403, exception.getStatusCode().value());
    }
}
