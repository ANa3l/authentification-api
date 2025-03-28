package com.api.authentification.controllers;

import com.api.authentification.config.JwtFilter;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LogoutController {

    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.warn("Déconnexion sans token.");
            return ResponseEntity.badRequest().body("Aucun token fourni.");
        }

        String token = authorizationHeader.substring(7);
        JwtFilter.revokeToken(token);
        logger.info("Token révoqué.");
        return ResponseEntity.ok().build(); // 200 ou 204
    }
}
