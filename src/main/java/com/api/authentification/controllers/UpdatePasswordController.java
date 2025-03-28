package com.api.authentification.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.authentification.dto.ChangePasswordPayloadDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.services.UpdatePasswordService;

import lombok.RequiredArgsConstructor;

/**
 * Contrôleur permettant à un utilisateur de changer son mot de passe.
 * L'utilisateur doit être authentifié via un token JWT.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UpdatePasswordController {

    private static final Logger logger = LoggerFactory.getLogger(UpdatePasswordController.class);
    private final UpdatePasswordService updatePasswordService;

    /**
     * Endpoint PUT /auth/register (devrait être renommé /update-password)
     * Met à jour le mot de passe d'un utilisateur authentifié.
     *
     * @param token JWT dans l'en-tête Authorization
     * @param request payload contenant l'ancien et le nouveau mot de passe
     * @return l'utilisateur mis à jour ou une erreur
     */
    @PutMapping("/register")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String token,
                                            @RequestBody ChangePasswordPayloadDTO request) {
        try {
            UserDTO result = updatePasswordService.updatePassword(token, request);
            return ResponseEntity.status(201).body(result);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du mot de passe", e);
            return ResponseEntity.internalServerError().body("Erreur serveur.");
        }
    }
}
